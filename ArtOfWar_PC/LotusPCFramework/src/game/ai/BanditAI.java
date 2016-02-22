package game.ai;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.ci.game.GameManager;
import com.ci.game.entity.Enemy;

public class BanditAI 
{
	Queue<CombatEvent> combatEvents = new LinkedBlockingQueue<CombatEvent>();
	
	public BanditAI()
	{
		
	}
	
	public void update()
	{
		
	}
	
	public LinkedList<Enemy> getBanditArmyEntities()
	{
		return GameManager.level.getEnemyArmyEntities();
	}

	public void processTurn() 
	{
		
		// if noone around entity to fight then walk towards cur target
		outerloop:
		for(int i = 0; i < GameManager.level.getEnemyArmyEntities().size(); i++)
		{
			for(int j = 0; j < GameManager.level.getPlayerArmyEntities().size(); j++)
			{
				if(	GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() + 1 == GameManager.level.getPlayerArmyEntities().get(j).getCenter64X() && GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() == GameManager.level.getPlayerArmyEntities().get(j).getCenter64Y())
				{
					combatEvents.add(new CombatEvent(i, j));
					//GameManager.level.engageCombat(i, j);
					
					break;
					//break outerloop;
				}
				else if(GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() - 1 == GameManager.level.getPlayerArmyEntities().get(j).getCenter64X() && GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() == GameManager.level.getPlayerArmyEntities().get(j).getCenter64Y())
				{
					//GameManager.level.engageCombat(i, j);
					combatEvents.add(new CombatEvent(i, j));

					break;
				}
				else if(GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() == GameManager.level.getPlayerArmyEntities().get(j).getCenter64X() && GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() + 1 == GameManager.level.getPlayerArmyEntities().get(j).getCenter64Y())
				{
					combatEvents.add(new CombatEvent(i, j));
					break;
				}
				else if(GameManager.level.getEnemyArmyEntities().get(i).getCenter64X() + 1 == GameManager.level.getPlayerArmyEntities().get(j).getCenter64X() && GameManager.level.getEnemyArmyEntities().get(i).getCenter64Y() - 1 == GameManager.level.getPlayerArmyEntities().get(j).getCenter64Y())
				{
					combatEvents.add(new CombatEvent(i, j));
					break;
				}
			}
		}
		
		while(!combatEvents.isEmpty() && combatEvents != null)
		{
			if(!GameManager.level.getPlayer().isCombatMode()&&!combatEvents.isEmpty())
			{

				CombatEvent e = combatEvents.poll();
				GameManager.level.engageEnemyCombat(e.getAttackerIndex(), e.getAttackeeIndex());
			}
		}
		combatEvents.clear();
	}
}
