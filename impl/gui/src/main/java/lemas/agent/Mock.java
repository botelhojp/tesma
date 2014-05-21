package lemas.agent;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.core.behaviours.Behaviour;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.KillContainer;
import jade.lang.acl.ACLMessage;
import openjade.core.OpenAgent;
import openjade.core.OpenJadeException;

public class Mock extends Agent{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		super.setup();
		moveContainer(OpenAgent.MAIN_CONTAINER);
		System.out.println("setup " + getAID().getLocalName());
		
		for (int index = 0; index < getArguments().length; index++) {
			System.out.println(getAID().getLocalName() + " - " + (String) getArguments()[index]);
		}
		
		addBehaviour( new Behaviour() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean done() {
				return false;
			}
			
			@Override
			public void action() {
				//System.out.println(getAID().getLocalName() + " action");
				
			}
		});
	}
	
	public synchronized void moveContainer(String to) {
		try {
			String from = getContainerController().getContainerName();
			if (!to.equals(from)) {
				ContainerID cid = new ContainerID(to, null);
				doMove(cid);
				KillContainer kill = new KillContainer();
				kill.setContainer(new ContainerID(from, null));
				Codec codec = new SLCodec();
				Ontology jmo = JADEManagementOntology.getInstance();
				getContentManager().registerLanguage(codec);
				getContentManager().registerOntology(jmo);
				ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
				msg.addReceiver(getAMS());
				msg.setLanguage(codec.getName());
				msg.setOntology(jmo.getName());
				getContentManager().fillContent(msg, new Action(getAID(), kill));
				send(msg);
			}
		} catch (Exception e) {
			throw new OpenJadeException(OpenAgent.MAIN_CONTAINER, e);
		}
	}

}
