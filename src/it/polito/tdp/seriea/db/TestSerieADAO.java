package it.polito.tdp.seriea.db;

import java.util.List;

import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class TestSerieADAO {

	public static void main(String[] args) {
		SerieADAO dao = new SerieADAO() ;
		
//		List<Season> seasons = dao.listSeasons() ;
//		System.out.println(seasons);
		
	Season s = new Season(2015,"2014/2015");
//		List<Team> teams = dao.getSquadreStag(s) ;
//		System.out.println(teams);

		
		List<Match> m = dao.getPartite(s) ;
		System.out.println(m);

	}

}
