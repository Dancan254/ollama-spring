package com.javaguy.ollamaspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class OllamaRAGService implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(OllamaRAGService.class);
    private final VectorStore vectorStore;
    @Value("classpath:/data/Blossoms of the Savannah.pdf")
    private Resource resource;

    public OllamaRAGService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }


    @Override
    public void run(String... args) throws Exception {
        //read the document
        TikaDocumentReader reader = new TikaDocumentReader(resource);
        //split it to chunks
        logger.info("Reading document...");
        TextSplitter splitter = new TokenTextSplitter();
        logger.info("Splitting document...");
        vectorStore.accept(splitter.split(reader.read()));
        logger.info("Document read and split");
    }
}
