package com.xml.search;

import lux.XdmResultSet;
import net.sf.saxon.s9api.XdmItem;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: dpavlov
 * Date: 14-10-21
 * Time: 10:26
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args){
        try {
            SearchEngine searchEngine = new SearchEngine("auction.xml");
            XdmResultSet resultSet = searchEngine.executeQuery("//site[ . ftcontains 'Sinisa Farrel']");

            //Print values
            Iterator<XdmItem> iterator = resultSet.iterator();
            while (iterator.hasNext()){
                XdmItem xdmItem = iterator.next();
                System.err.println(xdmItem.getStringValue());
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (XMLStreamException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
