package it.unisa.thesis.mosvi.utils.parser.bean;

public interface TextualSimilarityStrategy {
    Double textualSimilarity(String firstText, String secondText);
}
