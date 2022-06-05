/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.thesis.mosvi.utils.parser.bean;

import it.unisa.thesis.mosvi.utils.parser.bean.text.ApacheCosineSimilarityStrategy;
import it.unisa.thesis.mosvi.utils.parser.bean.text.IRNormalizer;
import org.apache.commons.text.similarity.CosineDistance;

import java.util.*;
import java.util.regex.Pattern;

/**
 *
 * @author fabiano
 */
public abstract class ComponentBean {

    private static final Pattern NEWLINE = Pattern.compile("\n");
    private static final Pattern SPACES = Pattern.compile("\\s+");

    protected String textContent;
    protected String name;
    protected List<CommentBean> comments;
    private final TextualSimilarityStrategy similarityStrategy;
    private final TextNormalizationStrategy normalizationStrategy;


    public ComponentBean(String name) {
        this.name = name;
        comments = new LinkedList<>();
        //TODO: Refactor to make them pure strategies
        similarityStrategy = new ApacheCosineSimilarityStrategy(new CosineDistance());
        normalizationStrategy = new IRNormalizer();
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public int getLOC() {
        return NEWLINE.split(textContent).length;
    }

    public int getCLOC() {
        int CLOC = 0;
        for(CommentBean commentBean: comments){
            CLOC += NEWLINE.split(commentBean.getCommentText()).length;
        }
        return CLOC;
    }

    public void addComment(CommentBean commentBean){
        comments.add(commentBean);
    }
    public abstract String getQualifiedName();

    public Double textualSimilarityWith(ComponentBean otherBean){
        return similarityStrategy.textualSimilarity(normalizedTextContent(),otherBean.normalizedTextContent());
    }

    public String normalizedTextContent(){
        return normalizationStrategy.normalizeText(textContent);
    }

    public Double wordsEntropy(){
        return calculateNormalizedShannonEntropy(Arrays.asList(SPACES.split(normalizedTextContent())));
    }

    private  Double calculateNormalizedShannonEntropy(List<String> values) {
        Map<String, Integer> map = new HashMap<>();
        // count the occurrences of each value
        for (String sequence : values) {
            if (!map.containsKey(sequence)) {
                map.put(sequence, 0);
            }
            map.put(sequence, map.get(sequence) + 1);
        }



        int sampleSize = map.keySet().size();
        if(sampleSize <= 1){
            return 0.0;
        }
        // calculate the entropy
        double result = 0.0;
        for (String sequence : map.keySet()) {
            double frequency = (double) map.get(sequence) / values.size();
            result += frequency * (Math.log(frequency) / Math.log(sampleSize));
        }

        return -result;
    }
}
