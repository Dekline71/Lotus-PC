package com.ci.game.entity;

public class Node 
{
	int h; // distance/spaces to node
	int f;
	int g; // move cost
	Node parent;
	
	public Node()
	{
		
	}
	
	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	
	public int getH()
	{
		return this.h;
	}
	
	public int getG()
	{
		return this.g;
	}
	
	public int getF()
	{
		return this.f;
	}
	
	public Node getParent()
	{
		return this.parent;
	}
}
