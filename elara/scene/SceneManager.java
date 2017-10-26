/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 26 Oct. 2017
 * File : SceneManager.java
 *
 * This software is open source and released under the MIT 
 * licence.
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.scene;

import java.io.File;
import java.io.IOException;
import org.json.simple.JSONObject;
import elara.editor.debug.Debug;
import elara.editor.debug.ElaraException;
import elara.editor.util.JSON;
import elara.project.EditingContext;
import elara.project.ProjectContext;
import elara.project.ProjectStruct;

/*****************************************************************
 *
 * SceneManager
 *
 * Description: Used to manage scenes. This is only to be used 
 * inside the project manager. All interaction still occurs through
 * the project manager.
 *
 *****************************************************************/

public class SceneManager
{
	public static final int MAX_SCENES = 100;

	private static SceneManager manager = new SceneManager();
	
	ProjectContext projCon = ProjectContext.projectContext();
	EditingContext editCon = EditingContext.editingContext();
	
	private SceneManager()
	{
	}
	
	/**
	 * Returns a static instance of SceneManager.
	 * @return
	 */
	public static SceneManager manager()
	{
		return manager;
	}
	
	/**
	 * Create a new scene in the current project.
	 * @param name
	 * @param width
	 * @param height
	 * @return
	 */
	public Scene createScene(String name, int width, int height) 
			throws ElaraException
	{
		// check project loaded and scene doesn't exist
		if (!projCon.isProjectLoaded()) {
			Debug.error("Tried to create a scene named: " + name + " while no project was loaded");
			throw new ElaraException("Tried to create a scene named: " + name + " while no project was loaded");
		}

		File sceneLocation = new File(projCon.projectPath() + "/" + ProjectStruct.SCENE_DIR + "/" + name);
		File configFile = new File(projCon.projectPath() + "/" + ProjectStruct.SCENE_DIR + "/" + name + "/" + SceneStruct.CONFIG);

		if (sceneLocation.exists()) {
			Debug.error("Scene already exists: " + name);
			throw new ElaraException("Scene already exists: " + name);
		}
		
		// create the scene
		Scene newScene = new Scene(name, width, height);
		new SceneLayer(newScene, "Default");
		
		// create the scene on disk
		sceneLocation.mkdirs();
		
		for (String sceneDir : SceneStruct.directoryList) {
			new File(sceneLocation.getAbsolutePath() + "/" + sceneDir).mkdirs();
		}
		
		File tmp = null;
		for (String sceneFile : SceneStruct.fileList) {
			try {
				tmp = new File(sceneLocation.getAbsolutePath() + "/" + sceneFile);
				tmp.createNewFile();
			} catch (IOException e) {
				Debug.error("Could not create file in scene directory: " + tmp.getAbsolutePath());
				throw new ElaraException("Could not create file in scene directory: " + tmp.getAbsolutePath());
			}
		}
		
		// create initial configuration and write it out
		JSONObject initObj = SceneStruct.initialJSONObj(name, width, height);
		
		try {
			JSON.write(initObj, configFile.getAbsolutePath());
		} catch (IOException e) {
			Debug.error("Could not write to new scene config: " + configFile.getAbsolutePath());
			throw new ElaraException("Could not write to new scene config: " + configFile.getAbsolutePath());
		}

		return newScene;
	}
}
