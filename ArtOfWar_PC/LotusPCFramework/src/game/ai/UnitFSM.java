package game.ai;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.ci.game.GameManager;
import com.ci.game.entity.Enemy;
import com.ci.game.entity.Enemy.UnitState;

public class UnitFSM
{
	private UnitState activeState;
	private Enemy enemy;
	private Queue<CombatEvent> combatEvents = new LinkedBlockingQueue<CombatEvent>();

	
	public UnitFSM(Enemy e)
	{
		this.setEnemy(e);
		this.activeState = UnitState.WANDER;
	
	}
	
	public void setState(Enemy.UnitState s)
	{
		this.activeState = s;
	}
	
	public void update()
	{
		if(activeState != null)
		{
			activeState();
		}
	}
	
	public void activeState()
	{
		switch(this.activeState)
		{		
			case WANDER:
				this.enemy.WANDER();
				break;
			
			case IDLE:
				this.enemy.IDLE();
				break;
				
			case MOVE_TO_TARGET:
				this.enemy.MOVE_TO_TARGET();
				break;
				
			case ATTACK:
				this.enemy.ATTACK();
				break;
				
			case DEFEND:
				break;
				
			case PRESET_TARGET:
				this.enemy.PRESET_TARGET();
				break;
			
		default:
			break;
		}
		
	}

	public UnitState getState()
	{
		return this.activeState;
	}
	
	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

	public Queue<CombatEvent> getCombatEvents() {
		return this.combatEvents;
	}

	public void setCombatEvents(Queue<CombatEvent> combatEvents) {
		this.combatEvents = combatEvents;
	}
}
