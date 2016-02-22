package game.ai;

public class CombatEvent 
{
	private int attackerIndex;
	private int attackeeIndex;
	
	public CombatEvent()
	{
		
	}
	
	public CombatEvent(int i , int j)
	{
		setAttackerIndex(i);
		setAttackeeIndex(j);
	}
	
	public int getAttackerIndex() 
	{
		return this.attackerIndex;
	}
	
	public void setAttackerIndex(int attackerIndex) 
	{
		this.attackerIndex = attackerIndex;
	}
	
	public int getAttackeeIndex()
	{
		return this.attackeeIndex;
	}
	
	public void setAttackeeIndex(int attackeeIndex) 
	{
		this.attackeeIndex = attackeeIndex;
	}
}
