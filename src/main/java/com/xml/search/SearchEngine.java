package com.xml.search;

import lux.Compiler;
import lux.*;
import lux.index.XmlIndexer;
import lux.search.LuxSearcher;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dpavlov
 * Date: 14-10-21
 * Time: 10:20
 * To change this template use File | Settings | File Templates.
 */
public class SearchEngine {

    private String fileName;
    private IndexWriter indexWriter;
    private FSDirectory fsDirectory;

    private XmlIndexer xmlIndexer;


    public SearchEngine(String filename) throws IOException, XMLStreamException {
        File document = new File("resources" + File.separator + filename);
        xmlIndexer = new XmlIndexer();
        fsDirectory = FSDirectory.open(new File("index"));
        indexWriter = xmlIndexer.newIndexWriter(fsDirectory);
        xmlIndexer.indexDocument(indexWriter, document.getAbsolutePath(), new FileInputStream(document));
        indexWriter.close();
    }

    public XdmResultSet executeQuery(String query) throws IOException {
        if(indexWriter != null){
            IndexReader indexReader = IndexReader.open(fsDirectory);
            LuxSearcher searcher = new LuxSearcher(indexReader);
            DocWriter docWriter = new DirectDocWriter(xmlIndexer, indexWriter);
            lux.Compiler compiler = new Compiler(xmlIndexer.getConfiguration());
            Evaluator evaluator = new Evaluator(compiler, searcher, docWriter);

            XdmResultSet resultSet = evaluator.evaluate(query);

            return resultSet;
        }
        return null;
    }

}
