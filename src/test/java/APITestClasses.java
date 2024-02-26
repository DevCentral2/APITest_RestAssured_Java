import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
class QuotesResponse {
    private int page;
    private boolean last_page;
    private List<QuoteResponse> quotes;
    public int getPage() {
        return page;
    }

    public boolean isLast_page() {
        return last_page;
    }

    public List<QuoteResponse> getQuotes() {
        return quotes;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)  // this attribute eliminates the error 'Unrecognized field "context" (class QuoteResponse)'

class QuoteResponse {
    private int id;
    private boolean dialogue;
    private boolean _private;
    private List<String> tags;
    private String url;
    private int favorites_count;
    private int upvotes_count;
    private int downvotes_count;
    private String author;
    private String author_permalink;
    private String body;
    private UserDetails user_details;

    // Getters and setters
    public int getId() {
        return id;
    }


    public int getFavorites_count() {
        return favorites_count;
    }


    public String getAuthor() {
        return author;
    }


    public String getBody() {
        return body;
    }


}

class UserDetails {
    private boolean favorite;
    private boolean upvote;
    private boolean downvote;
    private boolean hidden;

    // Constructor
    public UserDetails() {
    }
}

// This class is for use with the QuoteData list used to GetSpecificQuote

     class Quote {
        private int id;
        private String author;
        private String quoteText;

        public Quote(int id, String author, String quoteText) {
            this.id = id;
            this.author = author;
            this.quoteText = quoteText;
        }

         public int getId() {
             return id;
         }

         public String getAuthor() {
             return author;
         }

         public String getQuoteText() {
             return quoteText;
         }

    }
