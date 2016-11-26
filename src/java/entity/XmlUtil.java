/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import mb.LeagueMB;

/**
 *
 * @author Bakar M.M.R
 */
public class XmlUtil {

    private String path;

    public XmlUtil(String path) {
        this.path = path;
    }
    
    
    public void saveToXML(League league) throws JAXBException {
        try {
            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(League.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(league, file);
            jaxbMarshaller.marshal(league, System.out);

        } catch (JAXBException e) {
            
            throw e;
        }
    }

    public League getLeague() throws JAXBException {
        League league = null;
        try {
            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(League.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            league = (League) jaxbUnmarshaller.unmarshal(file);
            System.out.println(league);

        } catch (JAXBException e) {
            e.printStackTrace();
            throw e;
        }
        return league;
    }

    public List<String> generateMatch(Group group) {
        List<String> match = new ArrayList<>();

            for (int i = 0; i < group.getTeams().size(); i++) {
                for (int j = i + 1; j < group.getTeams().size(); j++) {
                    String home = "";
                    String way = "";
                    home += group.getTeams().get(i).getName() + " & " + group.getTeams().get(j).getName();
                    way += group.getTeams().get(j).getName() + " & " + group.getTeams().get(i).getName();
                    match.add(home);
                    match.add(way);
                }
            }
        return match;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
