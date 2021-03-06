package lemas.agent.behaviour;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import lemas.Lemas;
import lemas.Round;
import lemas.agent.AgentCache;
import lemas.agent.AgentLoader;
import lemas.agent.AgentOO;
import lemas.agent.ConversationId;
import lemas.agent.LemasAgent;
import lemas.db.CSV;
import lemas.exception.LemasException;
import lemas.form.FrameProject;
import lemas.model.LemasLog;
import lemas.model.Runner;
import lemas.trust.data.RatingCache;
import lemas.trust.metrics.Classes;
import lemas.trust.metrics.IMetrics;
import lemas.util.Data;
import lemas.util.Message;
import openjade.core.DataProvider;
import openjade.ontology.OpenJadeOntology;
import openjade.ontology.Rating;
import openjade.ontology.SendRating;
import openjade.trust.ITrustModel;
import openjade.trust.WitnessUtil;
import openjade.util.OpenJadeUtil;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class LoaderBehaviour extends Behaviour {

	private static final long serialVersionUID = 1L;

	private static final long CACHE_SIZE = 1000;

	private AgentLoader agent;
	private List<AID> agentCache_Client = new ArrayList<AID>();
	private List<AID> agentCache_Server = new ArrayList<AID>();
	private boolean done = false;
	private Long delay = null;
	private Instance instance = null;
	private long count = 0;
	// private int round = 1;
	private Class<ITrustModel> trustModelClass;
	private IMetrics metrics;
	int c = 0;

	private ArffReader arff;
	private BufferedReader reader;

	public LoaderBehaviour(AgentLoader _agent, Class<ITrustModel> trustModelClass, Class<IMetrics> metricsClass) {
		try {
			count = 0;
			agent = _agent;
			this.trustModelClass = trustModelClass;
			metrics = metricsClass.newInstance();
			loadArff();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void action() {
		if (agent.nowait()) {
			try {
				Instances data = arff.getStructure();
				Classes.getInstance(data);
				data.setClassIndex(data.numAttributes() - 1);
				instance = arff.readInstance(data);
				if (instance != null) {
					verifyDelay();
					metrics.preProcess(instance);
					
					Message.message(++count + " : " + instance.toString());
					DataProvider.getInstance().put("DATASET", instance.dataset());
					AID client = new AID("" + instance.toString(0), false);
					AID server = new AID("" + instance.toString(1), false);
					updateRound(instance.toString(2));
					agent.waiting();
					createAgent(server, "lemas.agent.LemasAgent", agentCache_Server);
					createAgent(client, "lemas.agent.LemasAgent", agentCache_Client);
					WitnessUtil.addWitness(server, client);
					sendTest(client, instance);
				} else {
					done = true;
					stop();
				}
			} catch (NumberFormatException e) {
				Message.message(e);				
			} catch (IOException e) {
				Message.message(e);	
			}
		}
		block(1);
	}

	private void verifyDelay() {
		try {
			if (FrameProject.getInstance().getCurrentProject().isStep()) {
				if (delay == null) {
					delay = (long) (1000 * Double.parseDouble(FrameProject.getInstance().getCurrentProject().getDelay()));
				}
				Thread.sleep(delay);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void updateRound(String date) {
		Round.getInstance().update(date);
	}

	public void loadArff() {
		try {
			agent.configure(Runner.currentProject);
			CSV.start(Runner.currentProject);
			File file = new File((String) Runner.currentProject.getArff());
			Message.message("abrindo arquivo [" + file.getAbsolutePath()+ "]");
			if (!file.exists()) {
				throw new LemasException("ARFF não informado");
			}
			reader = new BufferedReader(new FileReader(file));
			arff = new ArffReader(reader, 10);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void sendTest(AID client, Instance line) {
		if (line != null) {
			Rating rating = makeRating(line);
			RatingCache.put(rating.getRound(), rating);
			SendRating sr = new SendRating();
			sr.addRating(rating);

			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.setSender(agent.getAID());
			message.addReceiver(client);
			message.setConversationId(ConversationId.START_ITERATE);
			agent.fillContent(message, sr, agent.getCodec(), OpenJadeOntology.getInstance());
			if (FrameProject.getInstance().getSimulated()) {
				AgentCache.get(client.getLocalName()).message(message);
			} else {
				agent.sendMessage(message);
			}
		}
	}

	private Rating makeRating(Instance instance) {
		AID clientAID = new AID(instance.toString(0), false);
		AID serverAID = new AID(instance.toString(1), false);
		String value = instance.toString(instance.numAttributes() - 1);
		return OpenJadeUtil.makeRating(clientAID, serverAID, Round.getInstance().getRound(), Data.instanceToRatingAttribute(instance), value);
	}

	private void createAgent(AID aid, String clazz, List<AID> cache) {
		try {
			if (!cache.contains(aid)) {
				Object[] param = { trustModelClass };
				if (FrameProject.getInstance().getSimulated()) {
					if (!AgentCache.contains(aid.getLocalName())) {
						AgentCache.add(aid.getLocalName(), new AgentOO(aid.getLocalName(), param));
					}
				} else {
					AgentController a = agent.getContainerController().createNewAgent(aid.getLocalName(), clazz, param);
					a.start();
					cache.add(aid);
				}
				if (cache.size() > CACHE_SIZE) {
					AID deleteAid = cache.get(0);
					agent.sendMessage(deleteAid, ACLMessage.REQUEST, ConversationId.DO_DELETE, "");
				}
			}
		} catch (StaleProxyException e) {
			LemasLog.erro(e);
		} catch (Exception e) {
			LemasLog.erro(e);
		}
	}

	public void removerCache(AID sender) {
		if (agentCache_Client.contains(sender)) {
			agentCache_Client.remove(sender);
		}
		if (agentCache_Server.contains(sender)) {
			agentCache_Server.remove(sender);
		}
	}

	@Override
	public boolean done() {
		return done;
	}

	public void stop() {
		agent.sendMessage(LemasAgent.SERVICE, ACLMessage.REQUEST, ConversationId.DO_DELETE, "");
		if (myAgent != null) {
			myAgent.removeBehaviour(this);
		}
		Lemas.sleep(2000);
		AgentCache.clear();
		CSV.stop();
	}

	public double posProcess(ACLMessage msg) {
		return metrics.prosProcess(msg);
	}

}
