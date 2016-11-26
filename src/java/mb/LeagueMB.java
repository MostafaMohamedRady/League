/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import entity.Group;
import entity.League;
import entity.Team;
import entity.XmlUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.xml.bind.JAXBException;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Bakar M.M.R
 */
@ManagedBean
@ViewScoped
public class LeagueMB implements Serializable{

    private League league;
    private XmlUtil xmlUtil;
    private UploadedFile file;
    private boolean inValid;
    private String errorMessage = "";

    public LeagueMB() {
        inValid = false;
        try {
            String relativePath = "/resources/xml/League.xml";
            String absolutePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(relativePath);

            xmlUtil = new XmlUtil(absolutePath);
            league = xmlUtil.getLeague();

        } catch (JAXBException ex) {
            Logger.getLogger(LeagueMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> getMatch(Group group) {
        List<String> generateMatch = new ArrayList<>();
        generateMatch = xmlUtil.generateMatch(group);
        return generateMatch;
    }

    public void upload() {
        if (file != null) {
            String line = "";
            int groupNumber = 0;
            int teamCount = 0;
            League leagueUp = new League();
            try {
                Reader reader = new InputStreamReader(file.getInputstream());
                BufferedReader br = new BufferedReader(reader);
                List<Group> gs = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    groupNumber++;
                    Group group = new Group();
                    List<Team> ts = new ArrayList<>();
                    group.setNumber(groupNumber);
                    // use comma as separator
                    String[] teams = line.split(",");
                    if (teamCount == 0) {
                        teamCount = teams.length;
                    } else {
                        if (teamCount != teams.length) {
                            inValid = false;
                            //FacesMessage message = new FacesMessage("InValid Data");
                            //FacesContext.getCurrentInstance().addMessage(null, message);
                            errorMessage = "Invalid Data in line " + groupNumber;
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", errorMessage));
                        }
                    }
                    for (String team : teams) {
                        Team t = new Team();
                        t.setName(team);
                        ts.add(t);
                    }
                    group.setTeams(ts);
                    gs.add(group);
                }
                leagueUp.setGroups(gs);
                if (!inValid) {
                    xmlUtil.saveToXML(leagueUp);
                    league = xmlUtil.getLeague();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Data Uploaded successfully"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
        
    public boolean validateFile() {
        return true;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public XmlUtil getXmlUtil() {
        return xmlUtil;
    }

    public void setXmlUtil(XmlUtil xmlUtil) {
        this.xmlUtil = xmlUtil;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isInValid() {
        return inValid;
    }

    public void setInValid(boolean inValid) {
        this.inValid = inValid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
