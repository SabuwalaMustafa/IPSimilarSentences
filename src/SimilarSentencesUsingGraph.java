import java.util.*;
import java.util.stream.Collectors;

public class SimilarSentencesUsingGraph implements ISimilarSentencesDS {
    private final Graph graph;

    public SimilarSentencesUsingGraph() {
        graph = new Graph();
    }

    @Override
    public void addSynonymPair(String word1, String word2) {
        graph.addEdge(word1, word2);
    }

    @Override
    public void removeSynonymPair(String word1, String word2) {
        graph.removeEdge(word1, word2);
    }

    @Override
    public List<String> getSentences(String sentence) {
        List<List<String>> synonyms;
        synchronized (graph) {
            synonyms = Arrays.stream(sentence.split(" "))
                    .map(graph::getAllReachableNodes).collect(Collectors.toList());
        }
        List<String> similarSentences = new ArrayList<>();
        buildSimilarSentences(synonyms, new LinkedList<>(), similarSentences);
        return similarSentences.stream().filter(sentenceIter -> !sentenceIter.equals(sentence)).collect(Collectors.toList());
    }

    private void buildSimilarSentences(List<List<String>> synonyms, Queue<String> current, List<String> similarSentences) {
        if (synonyms.size() == current.size()) {
            similarSentences.add(String.join(" ", current));
            return;
        }
        for (String synonym : synonyms.get(current.size())) {
            current.add(synonym);
            buildSimilarSentences(synonyms, current, similarSentences);
            current.remove(synonym);
        }
    }
}
