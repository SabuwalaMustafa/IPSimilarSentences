import java.util.List;

public interface ISimilarSentencesDS {
    public void addSynonymPair(String word1, String word2);

    public void removeSynonymPair(String word1, String word2);

    public List<String> getSentences(String sentence);
}
