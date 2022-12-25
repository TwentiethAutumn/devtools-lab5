package ru.itmo.extended.todo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.sun.istack.NotNull;
import org.codehaus.jettison.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class CatFactPopup extends AnAction {
    public CatFactPopup() {
        super();
    }

    public String getWeather(){
        try{
            URL url = new URL("https://catfact.ninja/fact");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            String output = "";
            while ((line = reader.readLine()) != null) {
                output += line;
            }

            // Close the reader and connection.
            reader.close();
            connection.disconnect();

            JSONObject jsonObject = new JSONObject(output);


            return jsonObject.getString("fact");
        }catch(Exception ex){

        }
        return "Error";

    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project currentProject = event.getProject();
        Messages.showMessageDialog(currentProject, getWeather(), "Random cat fact :)", Messages.getInformationIcon());
    }

    @Override
    public void update(AnActionEvent e) {
        Project project = e.getProject();
        e.getPresentation().setEnabledAndVisible(project != null);
    }
}
