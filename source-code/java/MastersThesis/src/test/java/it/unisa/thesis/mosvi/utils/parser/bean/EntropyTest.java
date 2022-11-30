package it.unisa.thesis.mosvi.utils.parser.bean;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EntropyTest {

    @Test
    public void when_there_are_only_three_words_entropy_is_one() {
        ComponentBean aComponentBean = new ClassBean();
        aComponentBean.setName("aComponent");
        aComponentBean.setTextContent("create book store");
        assertThat(aComponentBean.wordsEntropy()).isEqualTo(1);
    }

    @Test
    public void when_there_is_only_one_term_entropy_is_0() {
        ComponentBean aComponentBean = new ClassBean();
        aComponentBean.setName("aComponent");
        aComponentBean.setTextContent("book book book");
        assertThat(aComponentBean.wordsEntropy()).isEqualTo(0);
    }
}
