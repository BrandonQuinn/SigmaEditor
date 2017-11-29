/*****************************************************************
 *
 * Author: Brandon
 * Date Created: 26 Nov. 2017
 * File : EditorConfiguration.java
 *
 * Copyright (c) 2017 Brandon Quinn
 *
 *****************************************************************/

package elara.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import elara.editor.util.JSON;

/*****************************************************************
 *
 * EditorConfiguration
 *
 * Description: Represents the editor configuration file
 * which stores any settings that need to remain after the
 * editor has been closed.
 *
 *****************************************************************/

public class EditorConfiguration
{
    public static final String CONFIG_DIR = "conf/";
    private static File configDir = new File(CONFIG_DIR);

    public static final String CONFIG = "conf/editor.config";
    private static File configFile = new File(CONFIG);

    private static JSONObject configObject;

    public static class RecentProject
    {
        public String name;
        public File projectLocation;

        public RecentProject(String name, File path)
        {
            this.name = name;
            this.projectLocation = path;
        }
    }

    /**
     * Read the existing config file and parse it, otherwise
     * if it does not exist create a new one and write out the
     * JSON template.
     */
    public static void init()
        throws IOException,
        ParseException,
        FileNotFoundException
    {
        if (!configDir.exists()) configDir.mkdir();
        if (!configFile.exists()) {
            configFile.createNewFile();
            writeTemplate();
        }
        configObject = JSON.read(configFile);
    }

    /**
     * Adds a recent project to the editor configuration, however, if a project
     * with the same name and name only already exists then this method will
     * not make any changes to the editor configuration.
     *
     * @param name
     * @param projectLocation
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public static void addRecentProject(String name, File projectLocation)
        throws IOException
    {
        JSONArray recentProjects = getArray("recentProjects");
        for (int i = 0; i < recentProjects.size(); i++) {
            JSONObject rp = (JSONObject) recentProjects.get(i);
            String nameInFile = (String) rp.get("name");

            if (nameInFile.equals(name)) {
                return; // already exists, so skip
            }
        }

        // add the new project
        JSONObject newRecentProject = new JSONObject();
        newRecentProject.put("name", name);
        newRecentProject.put("path", projectLocation.getAbsolutePath());
        recentProjects.add(newRecentProject);
        configObject.replace("recentProjects", recentProjects);
        save();
    }

    /**
     * Returns an array list of all recent projects listed in the editor
     * configuration.
     */
    public static ArrayList<RecentProject> recentProjects()
        throws IOException
    {
        ArrayList<RecentProject> recentProjects = new ArrayList<RecentProject>();
        JSONArray recentProjectsJSON = getArray("recentProjects");
        for (int i = 0; i < recentProjectsJSON.size(); i++) {
            JSONObject rp = (JSONObject) recentProjectsJSON.get(i);
            String name = (String) rp.get("name");
            File path = new File((String) rp.get("path"));
            RecentProject newRecentProject = new RecentProject(name, path);
            recentProjects.add(newRecentProject);
        }
        return recentProjects;
    }

    public static JSONArray getArray(String name)
    {
        return (JSONArray) configObject.get(name);
    }

    private static void save()
        throws IOException
    {
        JSON.write(configObject, configFile.getAbsolutePath());
    }

    /**
     * Simply write out the blank JSON template for the editor
     * configuration.
     */
    @SuppressWarnings("unchecked")
	private static void writeTemplate()
        throws IOException
    {
        configObject = new JSONObject();
        JSONArray recentProjectsArray = new JSONArray();
        configObject.put("recentProjects", recentProjectsArray);
        JSON.write(configObject, configFile.getAbsolutePath());
    }
}
