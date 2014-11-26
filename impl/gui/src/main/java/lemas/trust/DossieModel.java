package lemas.trust;

import java.util.ArrayList;
import java.util.List;

import lesma.annotations.TrustModel;
import openjade.ontology.Rating;

@TrustModel(name = "Dossie Model")
public class DossieModel extends AbstractModel {

	protected List<Rating> dossie = new ArrayList<Rating>();

	private static final long serialVersionUID = 1L;

	@Override
	public void addRating(Rating rating, boolean direct) {
		super.addRating(rating, direct);
		if (isIamClient(rating)) {
			if (direct){
				myAgent.requestDossie(rating.getServer());
			}
		} else {
			dossie.add(rating);
		}
	}

	public List<Rating> getDossie() {
		return dossie;
	}
}