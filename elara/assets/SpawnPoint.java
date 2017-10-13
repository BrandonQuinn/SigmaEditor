/**
 * Author: Brandon
 * Date Created: 13 Oct. 2017
 * File : SpawnPoint.java
 */
package elara.assets;

/**
 * SpawnPoint
 *
 * Description: A location to spawn for a particular team.
 */
public class SpawnPoint extends Entity
{
	private int team = 0;
	
	public SpawnPoint() 
	{
		
	}
	
	/**
	 * Set the team which can spawn at this spawn point.
	 * @param team
	 */
	public void assignTeam(int team)
	{
		this.team = team;
	}
	
	/**
	 * Returns the team which can spawn at this spawn points.
	 * @return
	 */
	public int team()
	{
		return team;
	}
}
