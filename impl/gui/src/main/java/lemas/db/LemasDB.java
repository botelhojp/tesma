package lemas.db;

import jade.core.AID;
import jade.core.Agent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import lemas.trust.TrustModelData;

import com.google.gson.Gson;

public class LemasDB {

	private Connection conn;

	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/agent", "postgres", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss-SS");
		for (int i = 0; i < 100; i++) {
			System.out.println(f.format(((Calendar) GregorianCalendar.getInstance()).getTime()));	
		}
	}

	public void save(Agent myAgent, HashMap<AID, TrustModelData> data) {
		try {
			if (!exist(myAgent)) {
				Statement st = conn.createStatement();
				Gson gson = new Gson();
				String json = gson.toJson(data);
				st.executeUpdate("INSERT INTO agent " + "VALUES ('" + myAgent.getLocalName() + "', '" + json + "')");
				st.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public HashMap<AID, TrustModelData> load(Agent agent) {
		try {
			if (exist(agent)) {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("SELECT content FROM agent WHERE aid = '" + agent.getLocalName() + "'");
				rs.next();

				Gson gson = new Gson();
				String content = rs.getString("content");
				HashMap<AID, TrustModelData> data = gson.fromJson(content, HashMap.class);
				rs.close();
				st.close();
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean exist(Agent agent) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM agent WHERE aid = '" + agent.getLocalName() + "'");
			boolean r = rs.next();
			rs.close();
			st.close();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void cleanAll() {
		try {
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM agent");
			st.executeUpdate("DELETE FROM result");
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void save(int executions, double round, double value) {
		try {
			
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timer = f.format(((Calendar) GregorianCalendar.getInstance()).getTime());	
			
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO result " + "VALUES ('" + executions + "', '" + round + "', '" + value + "', '"+timer+"'  )");
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
