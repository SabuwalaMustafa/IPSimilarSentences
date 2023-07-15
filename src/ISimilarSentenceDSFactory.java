public class ISimilarSentenceDSFactory {
    public static ISimilarSentencesDS getISimilarSentencesDS() {
        return new SimilarSentencesUsingGraph();
    }
}
