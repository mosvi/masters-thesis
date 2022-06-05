package it.unisa.thesis.mosvi.utils.parser.bean.text;

import it.unisa.thesis.mosvi.utils.parser.bean.TextNormalizationStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IRNormalizer implements TextNormalizationStrategy {

    @Override
    public String normalizeText(String inputTextualContent) {
        List<String> terms = extractTerms(inputTextualContent);
        terms = removeKeywords(terms);
        terms = splitCamelCaseWords(terms);
        terms = deleteNumbers(terms);
        terms = toLowerCaseAll(terms);
        String textContent = StringUtils.join(terms, StringUtils.SPACE);
        return StringUtils.trim(Stopwords.removeStopWords(textContent));
    }

    private List<String> toLowerCaseAll(List<String> terms) {
        return terms.stream().map(StringUtils::lowerCase).collect(Collectors.toList());
    }

    private List<String> removeKeywords(List<String> terms) {
        return terms.stream().filter(term -> !isKeyword(term)).collect(Collectors.toList());
    }

    private List<String> splitCamelCaseWords(List<String> terms) {
        return terms.stream().map(this::splitCamelCase).collect(Collectors.toList());
    }

    private String splitCamelCase(String term) {
        String[] split = term.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
        return StringUtils.join(split, StringUtils.SPACE);
    }

    private List<String> deleteNumbers(List<String> terms) {
        return terms.stream().filter(term -> !isNumber(term)).collect(Collectors.toList());
    }

    private boolean isNumber(String term){
        return StringUtils.isNumeric(term);
    }

    private List<String> extractTerms(String classTextContent) {
        return Arrays.asList(classTextContent.split("\\W"));
    }

    private boolean isKeyword(String pWord) {
        if ((pWord.equalsIgnoreCase("for")) || (pWord.equalsIgnoreCase("if")) || (pWord.equalsIgnoreCase("while")) || (pWord.equalsIgnoreCase("else")) ||
                (pWord.equalsIgnoreCase("continue")) || (pWord.equalsIgnoreCase("int")) || (pWord.equalsIgnoreCase("String")) || (pWord.equalsIgnoreCase("float")) ||
                (pWord.equalsIgnoreCase("double")) || (pWord.equalsIgnoreCase("return")) || (pWord.equalsIgnoreCase("false")) || (pWord.equalsIgnoreCase("true")) ||
                (pWord.equalsIgnoreCase("char")) || (pWord.equalsIgnoreCase("boolean")) || (pWord.equalsIgnoreCase("public")) || (pWord.equalsIgnoreCase("private")) ||
                (pWord.equalsIgnoreCase("protected")) || (pWord.equalsIgnoreCase("class")) || (pWord.equalsIgnoreCase("Integer")) || (pWord.equalsIgnoreCase("Double")) ||
                (pWord.equalsIgnoreCase("Boolean")) || (pWord.equalsIgnoreCase("Character")) || (pWord.equalsIgnoreCase("import")) || (pWord.equalsIgnoreCase("package")) ||
                (pWord.equalsIgnoreCase("null")) || (pWord.equalsIgnoreCase("this")) || (pWord.equalsIgnoreCase("Float")) || (pWord.equalsIgnoreCase("abstract")) ||
                (pWord.equalsIgnoreCase("new")) || (pWord.equalsIgnoreCase("switch")) || (pWord.equalsIgnoreCase("assert")) || (pWord.equalsIgnoreCase("default")) ||
                (pWord.equalsIgnoreCase("goto")) || (pWord.equalsIgnoreCase("synchronized")) || (pWord.equalsIgnoreCase("do")) || (pWord.equalsIgnoreCase("break")) ||
                (pWord.equalsIgnoreCase("implements")) || (pWord.equalsIgnoreCase("throw")) || (pWord.equalsIgnoreCase("byte")) || (pWord.equalsIgnoreCase("throws")) ||
                (pWord.equalsIgnoreCase("case")) || (pWord.equalsIgnoreCase("enum")) || (pWord.equalsIgnoreCase("instanceof")) || (pWord.equalsIgnoreCase("transient")) ||
                (pWord.equalsIgnoreCase("catch")) || (pWord.equalsIgnoreCase("extends")) || (pWord.equalsIgnoreCase("short")) || (pWord.equalsIgnoreCase("try")) ||
                (pWord.equalsIgnoreCase("final")) || (pWord.equalsIgnoreCase("interface")) || (pWord.equalsIgnoreCase("statico")) || (pWord.equalsIgnoreCase("void")) ||
                (pWord.equalsIgnoreCase("finally")) || (pWord.equalsIgnoreCase("long")) || (pWord.equalsIgnoreCase("strictfp")) || (pWord.equalsIgnoreCase("volatile")) ||
                (pWord.equalsIgnoreCase("const")) || (pWord.equalsIgnoreCase("native")) || (pWord.equalsIgnoreCase("super")) || (pWord.equalsIgnoreCase("Long")) ||
                (pWord.equalsIgnoreCase("Byte")) || (pWord.equalsIgnoreCase("Short")) || (pWord.equalsIgnoreCase("exception")) || (pWord.equalsIgnoreCase("org")) ||
                (pWord.equalsIgnoreCase("eclipse")) || (pWord.equalsIgnoreCase("FileNotFoundException")) || (pWord.equalsIgnoreCase("IOException")) ||
                (pWord.equalsIgnoreCase("NotValidValueException")) || (pWord.equalsIgnoreCase("Object")) || (pWord.equalsIgnoreCase("println") || (pWord.equalsIgnoreCase("System")) ||
                (pWord.equalsIgnoreCase("out")) || (pWord.equalsIgnoreCase("JOptionPane")) || (pWord.equalsIgnoreCase("equalsIgnoreCase")) || (pWord.equalsIgnoreCase("equalsIgnoreCaseIgnoreCase")) ||
                (pWord.equalsIgnoreCase("ArrayList")) || (pWord.equalsIgnoreCase("List")) || (pWord.equalsIgnoreCase("Vector")) || (pWord.equalsIgnoreCase("Set")) || (pWord.equalsIgnoreCase("HashMap")) ||
                (pWord.equalsIgnoreCase("Map")) || (pWord.equalsIgnoreCase("SortedSet")) || (pWord.equalsIgnoreCase("SortedMap")) || (pWord.equalsIgnoreCase("TreeSet")) ||
                (pWord.equalsIgnoreCase("LinkedList")) || (pWord.equalsIgnoreCase("TreeMap")) || (pWord.equalsIgnoreCase("Properties")) || (pWord.equalsIgnoreCase("Stack")) ||
                (pWord.equalsIgnoreCase("Hashtable")) || (pWord.equalsIgnoreCase("Iterator")) || (pWord.equalsIgnoreCase("AbstractCollection")) || (pWord.equalsIgnoreCase("ListIterator")) ||
                (pWord.equalsIgnoreCase("AbstractSequentialList")) || (pWord.equalsIgnoreCase("AbstractMap")) || (pWord.equalsIgnoreCase("Comparable")) ||
                (pWord.equalsIgnoreCase("BigDecimal")) || (pWord.equalsIgnoreCase("BigInteger")) || (pWord.equalsIgnoreCase("CollationKey")) || (pWord.equalsIgnoreCase("ObjectStreamField")) ||
                (pWord.equalsIgnoreCase("Date")) || (pWord.equalsIgnoreCase("Collator")) || (pWord.equalsIgnoreCase("Comparator")) || (pWord.equalsIgnoreCase("java")) || (pWord.equalsIgnoreCase("util")) ||
                (pWord.equalsIgnoreCase("arraycopy")) || (pWord.equalsIgnoreCase("Enumeration")) || (pWord.equalsIgnoreCase("Collections")) || (pWord.equalsIgnoreCase("Dimension")) ||
                (pWord.equalsIgnoreCase("Arrays")) || (pWord.equalsIgnoreCase("SortedListModel")) || (pWord.equalsIgnoreCase("connection")) || (pWord.equalsIgnoreCase("get")) ||
                (pWord.equalsIgnoreCase("set")) || (pWord.equalsIgnoreCase("is")) || (pWord.equalsIgnoreCase("main")) || (pWord.equalsIgnoreCase("args")) || (pWord.equalsIgnoreCase("argv")))) {
            return true;
        } else return pWord.contains("Exception");
    }

}
