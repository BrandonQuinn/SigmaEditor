/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 16 Nov. 2017
 * File : Builder.java
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.build;

import java.io.File;
import java.util.concurrent.Semaphore;
import elara.builder.projectbuild.ProjectBuilder;
import elara.editor.debug.Debug;
import elara.editor.debug.ElaraException;
import elara.project.ProjectContext;

/*****************************************************************
 *
 * Builder
 *
 * Description: Used to take the currently loaded project and
 * build it in to a destination directory when is the
 * final product. 
 *
 * Uses the external build tool to execute the build. This is
 * to just to modularise or separate the duties.
 *
 * I want to make sure builds can be done externally.
 *
 *****************************************************************/

public class Builder
	implements Runnable
{
	private static ProjectContext projCon = ProjectContext.projectContext();

	// Blocks multiple calls to the build routine
	// to prevent any problems when writing and 
	// reading files.
	private static Semaphore buildSem = new Semaphore(1);
	
	private static File projectDirectory;
	private static File outputDirectory;
	
	private Builder() {}

	/**
	 * Build the project and put it in to the destination
	 * directory.
	 */
	public static void build(File projectDirectory, File outputDirectory)
		throws ElaraException
	{
		if (!projCon.isProjectLoaded()) {
			Debug.error("No project loaded when initiating build");
			throw new ElaraException("No project loaded when initiating build");
		}
		
		// block execution of the build if the semaphore ticket is already acquired
		if (!Builder.buildSem.tryAcquire()) {
			Debug.error("Semaphore aquisition failed, semaphore already aquired");
			throw new ElaraException("Semaphore aquisition failed, semaphore already aquired");
		}
		
		Builder.projectDirectory = projectDirectory;
		Builder.outputDirectory = outputDirectory;
		
		Builder builder = new Builder();
		Thread buildThread = new Thread(builder);
		buildThread.start();
	}

	@Override
	public void run()
	{
		ProjectBuilder projectBuilder = new ProjectBuilder();
		projectBuilder.setProjectDir(projectDirectory.getAbsolutePath());
		projectBuilder.setOutputDirectory(outputDirectory.getAbsolutePath());
		projectBuilder.build();
		Builder.buildSem.release();
	}
}
