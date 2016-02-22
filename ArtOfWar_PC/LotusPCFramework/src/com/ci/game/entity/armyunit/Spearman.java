package com.ci.game.entity.armyunit;

import com.ci.game.entity.Entity;
import com.ci.game.entity.Entity.UnitClass;
import com.ci.game.entity.Entity.UnitType;
import com.ci.game.graphics.Assets;
import com.ci.game.graphics.Camera;
import com.ci.game.level.Level;

public class Spearman extends ArmyUnit 
{
public Spearman(){}
	
	public Spearman(Entity e)
	{
		super(e);
	}
	
	public Spearman(int x, int y, Level l, UnitType t, UnitClass c)
	{
		super(x, y, l, t, c);
	}
	
	public void render(Camera screen)
	{
		if(direction == 0)
		{
			if(walking)
			{
				if(anim % 20 > 10)
				{
					this.a = Assets.peasAnimUp;
					setImage(a.getImage());
				}
				else if(this.isCombatMode)
				{
					this.a = Assets.peasAnimUpAtk;
					setImage(a.getImage());
				}
			}
		}
		
		if(direction == 1)
		{
			if(walking)
			{
				if(anim % 20 > 10)
				{
					this.a = Assets.peasAnimRight;
					setImage(a.getImage());
				}
				else if(this.isCombatMode)
				{
					this.a = Assets.peasAnimRightAtk;
					setImage(a.getImage());
				}
			}
		}
		
		if(direction == 2)
		{
			if(walking)
			{
				if(anim % 20 > 10)
				{
					this.a = Assets.peasAnimDown;
					setImage(a.getImage());
				}
				else if(this.isCombatMode)
				{
					this.a = Assets.peasAnimDownAtk;
					setImage(a.getImage());
				}
			}
		}
		
		if(direction == 3)
		{
			if(walking)
			{
				if(anim % 20 > 10)
				{
					this.a = Assets.peasAnimLeft;
					setImage(a.getImage());
				}
				else if(this.isCombatMode)
				{
					this.a = Assets.peasAnimLeftAtk;
					setImage(a.getImage());
				}
			}
		}
		//screen.renderArmyEntity(getCenter64X(), getCenter64Y(), this);
		screen.renderArmyEntity( this);

	}
}
