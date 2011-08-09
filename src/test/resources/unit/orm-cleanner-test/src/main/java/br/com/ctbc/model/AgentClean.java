package br.com.ctbc.maestro.vantive.domain.agent;









import br.com.ctbc.maestro.vantive.person.Person;



public class Agent implements br.com.ctbc.maestro.vantive.api.agent.Agent {

	private static final long serialVersionUID = 6099709322372516547L;

	
	
	private Long agentId;

	
	private String usage;

	
	private Integer active;

	
	private Long busUnitId;

	
	
	private Person person;

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public boolean isActive() {
		return active == 1;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Long getBusUnitId() {
		return busUnitId;
	}

	public void setBusUnitId(Long busUnitId) {
		this.busUnitId = busUnitId;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((agentId == null) ? 0 : agentId.hashCode());
		result = prime * result
				+ ((busUnitId == null) ? 0 : busUnitId.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
		result = prime * result + ((usage == null) ? 0 : usage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agent other = (Agent) obj;
		if (agentId == null) {
			if (other.agentId != null)
				return false;
		} else if (!agentId.equals(other.agentId))
			return false;

		return true;
	}

}
