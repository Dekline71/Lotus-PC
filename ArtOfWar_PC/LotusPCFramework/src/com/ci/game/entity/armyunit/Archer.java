package com.ci.game.entity.armyunit;

import com.ci.game.entity.Entity;
import com.ci.game.graphics.Assets;
import com.ci.game.graphics.Camera;
import com.ci.game.level.Level;

public class Archer extends ArmyUnit
{
	public Archer(){}
	
	public Archer(Entity e)
	{
		super(e);
	}
	
	public Archer(int x, int y, Level l, UnitType t, UnitClass c)
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
					this.a = Assets.archerAnimUp;
					setImage(a.getImage());
				}
				else if(this.isCombatMode)
				{
					this.a = Assets.archerAttackAnimUp;
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
					this.a = Assets.archerAnimRight;
					setImage(a.getImage());
				}
				else if(this.isCombatMode)
				{
					this.a = Assets.archerAttackAnimRight;
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
					this.a = Assets.archerAnimDown;
					setImage(a.getImage());
				}
				else if(this.isCombatMode)
				{
					this.a = Assets.archerAttackAnimDown;
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
					this.a = Assets.archerAnimLeft;
					setImage(a.getImage());
				}
				else if(this.isCombatMode)
				{
					this.a = Assets.archerAttackAnimLeft;
					setImage(a.getImage());
				}
			}
		}
		//screen.renderArmyEntity(getCenter64X(), getCenter64Y(), this);
		screen.renderArmyEntity( this);

	}
	
}
