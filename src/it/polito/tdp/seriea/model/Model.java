package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SerieADAO dao;
	private DirectedWeightedMultigraph<Team,DefaultWeightedEdge> grafo;
	
	public Model(){
		this.dao= new SerieADAO();
	}

	public List<Season> getStagioni(){
		return dao.listSeasons();
	}
	
	private void creaGrafo(Season s){
		this.grafo = new DirectedWeightedMultigraph<Team,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.getSquadreStag(s));
		
		List<Match> matches= dao.getPartite(s);
		
		for(Team ht : grafo.vertexSet()){
			for(Match m : matches){
				if(ht.equals(m.getHomeTeam())){
					Team at= m.getAwayTeam();
					DefaultWeightedEdge e = grafo.addEdge(ht, at);
					grafo.setEdgeWeight(e, m.getPeso());
				}
			}
		}		
	}
	
	public List<Team> getClassifica(Season s){
		this.creaGrafo(s);
		Map<String, Team> team = new HashMap<String,Team>();
		for(Team ttt: grafo.vertexSet())
			team.put(ttt.getTeam(), ttt);
		for (DefaultWeightedEdge e : grafo.edgeSet()){
			//System.out.println(grafo.getEdgeWeight(e));
			Team t=team.get(grafo.getEdgeSource(e).getTeam());
			Team tt= team.get(grafo.getEdgeTarget(e).getTeam());
			int  p=(int)grafo.getEdgeWeight(e);
			if(p==0){
				t.addPareggio();
				tt.addPareggio();
			}
			if(p==1){
				t.addVittoria();
				tt.addSconfitta();
			}
			if(p==-1){
				t.addSconfitta();
				tt.addVittoria();
			}
			//System.out.println(e+" "+t);
		}
		List<Team> l = new ArrayList<>(team.values());
		Collections.sort(l);
		
		return l;
	}
}
