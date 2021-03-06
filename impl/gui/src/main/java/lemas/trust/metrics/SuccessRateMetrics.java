package lemas.trust.metrics;

import jade.lang.acl.ACLMessage;
import lesma.annotations.Metrics;
import weka.core.Instance;

@Metrics(name = "Success Rate AVG", file = "sucess_rate_avg")
public class SuccessRateMetrics extends AbstractIMetric {
	
	private double countFalse = 0;
	private double count = 0;

	public void preProcess(Instance instance) {
	}

	public double prosProcess(ACLMessage msg) {
		++count;
		Boolean test = Boolean.parseBoolean(msg.getContent());
		if (!test) {
			countFalse++;
		}
		double value = (1.00 - countFalse/count);
		return value;
	}

}
