package it.unisa.thesis.mosvi.utils.parser.bean.text;

import it.unisa.thesis.mosvi.utils.parser.bean.TextualSimilarityStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.CosineDistance;

public class ApacheCosineSimilarityStrategy implements TextualSimilarityStrategy {

    private final CosineDistance distance;

    public ApacheCosineSimilarityStrategy(CosineDistance distance) {
        this.distance = distance;
    }

    @Override
    public Double textualSimilarity(String firstText, String secondText) {
        if(!isValid(firstText) || !isValid(secondText)) {
            return 0.0;
        }
        double value = 1 - distance.apply(firstText, secondText);
        return twoDecimalDigitsApproximation(value);
    }

    private boolean isValid(String text){
        return StringUtils.isNotBlank(text);
    }

    private double twoDecimalDigitsApproximation(double value) {
        return Math.floor(value * 100) / 100;
    }
}
