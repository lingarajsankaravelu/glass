package lingaraj.hourglass.in.glass.smshomescreen;

public class ShortMessage {
   private String date;
   private String address;
   private String body;


  public ShortMessage(String date, String address, String body) {
    this.date = date;
    this.address = address;
    this.body = body;
  }

  public String getAddress() {
    return address;
  }

  public String getDate() {
    return date;
  }


  public String getBody() {
    return body;
  }
}
