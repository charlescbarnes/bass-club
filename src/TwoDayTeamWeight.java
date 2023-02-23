public class TwoDayTeamWeight {
    private int place = 0;
    private Angler boater;
    private boolean boaterFishedDay1 = false;
    private int boaterDay1Fish;
    private int boaterDay1FishAlive;
    private double boaterDay1Weight;
    private String boaterDay1BigBass;
    private boolean boaterFishedDay2 = false;
    private int boaterDay2Fish;
    private int boaterDay2FishAlive;
    private double boaterDay2Weight;
    private String boaterDay2BigBass;

    private Angler coAngler;
    private boolean coAnglerFishedDay1 = false;
    private int coAnglerDay1Fish;
    private int coAnglerDay1FishAlive;
    private double coAnglerDay1Weight;
    private String coAnglerDay1BigBass;
    private boolean coAnglerFishedDay2 = false;
    private int coAnglerDay2Fish;
    private int coAnglerDay2FishAlive;
    private double coAnglerDay2Weight;
    private String coAnglerDay2BigBass;
    private double totalCombinedWeight;

    /**
     * Class constructor
     * @param boater                an <code>Angler</code>
     * @param boaterDay1Fish        an <code>int</code> number of fish weighed on Day 1
     * @param boaterDay1FishAlive   an <code>int</code> number of fish weighed alive on Day 1
     * @param boaterDay1Weight      a <code>double</code> Day 1 bag weight
     * @param boaterDay1BigBass     a <code>double</code> big bass weight on Day 1
     * @param boaterDay2Fish        an <code>int</code> number of fish weighed on Day 2
     * @param boaterDay2FishAlive   an <code>int</code> number of fish weighed alive on Day 2
     * @param boaterDay2Weight      a <code>double</code> Day 2 bag weight
     * @param boaterDay2BigBass     a <code>double</code> big bass weight on Day 2
     * @param coAngler              an <code>Angler</code>
     * @param coDay1Fish            an <code>int</code> number of fish weighed on Day 1
     * @param coDay1FishAlive       an <code>int</code> number of fish weighed alive on Day 1
     * @param coDay1Weight          a <code>double</code> Day 1 bag weight
     * @param coDay1BigBass         a <code>double</code> big bass weight on Day 1
     * @param coDay2Fish            an <code>int</code> number of fish weighed on Day 2
     * @param coDay2FishAlive       an <code>int</code> number of fish weighed alive on Day 2
     * @param coDay2Weight          a <code>double</code> Day 2 bag weight
     * @param coDay2BigBass         a <code>double</code> big bass weight on Day 2
     */
    public TwoDayTeamWeight(Angler boater, int boaterDay1Fish, int boaterDay1FishAlive,
                            double boaterDay1Weight, String boaterDay1BigBass,
                            int boaterDay2Fish, int boaterDay2FishAlive,
                            double boaterDay2Weight, String boaterDay2BigBass,
                            Angler coAngler, int coDay1Fish, int coDay1FishAlive,
                            double coDay1Weight, String coDay1BigBass,
                            int coDay2Fish, int coDay2FishAlive,
                            double coDay2Weight, String coDay2BigBass) {
        this.boater = boater;
        this.boaterDay1Fish = boaterDay1Fish;
        this.boaterDay1FishAlive = boaterDay1FishAlive;
        this.boaterDay1Weight += boaterDay1Weight;
        this.boaterDay1BigBass = boaterDay1BigBass;
        this.boaterDay2Fish = boaterDay2Fish;
        this.boaterDay2FishAlive = boaterDay2FishAlive;
        this.boaterDay2Weight += boaterDay2Weight;
        this.boaterDay2BigBass = boaterDay2BigBass;
        this.coAngler = coAngler;
        coAnglerDay1Fish = coDay1Fish;
        coAnglerDay1FishAlive = coDay1FishAlive;
        coAnglerDay1Weight += coDay1Weight;
        coAnglerDay1BigBass = coDay1BigBass;
        coAnglerDay2Fish = coDay2Fish;
        coAnglerDay2FishAlive = coDay2FishAlive;
        coAnglerDay2Weight += coDay2Weight;
        coAnglerDay2BigBass = coDay2BigBass;
        totalCombinedWeight = boaterDay1Weight + boaterDay2Weight
                            + coAnglerDay1Weight + coAnglerDay2Weight;
    }

    public Angler getBoater() { return boater; }
    public Angler getCoAngler() { return coAngler; }
    public double getTotalCombinedWeight() { return totalCombinedWeight; }
    public void setPlace(int i) { place = i; }
    public void setBoaterFishedDay1(boolean bool) { boaterFishedDay1 = bool; }
    public void setBoaterFishedDay2(boolean bool) { boaterFishedDay2 = bool; }
    public void setBoaterDay2Fish(int num) { boaterDay2Fish = num; }
    public void setBoaterDay2FishAlive(int num) { boaterDay2FishAlive = num; }
    public void setBoaterDay2Weight(double weight) { boaterDay2Weight = weight; }
    public void setBoaterDay2BigBass(String bb) { boaterDay2BigBass = bb; }
    public void setCoAnglerFishedDay1(boolean bool) { coAnglerFishedDay1 = bool; }
    public void setCoAnglerFishedDay2(boolean bool) { coAnglerFishedDay2 = bool; }
    public void setCoAnglerDay2Fish(int num) { coAnglerDay2Fish = num; }
    public void setCoAnglerDay2FishAlive(int num) { coAnglerDay2FishAlive = num; }
    public void setCoAnglerDay2Weight(double weight) { coAnglerDay2Weight = weight; }
    public void setCoAnglerDay2BigBass(String bb) { coAnglerDay2BigBass = bb; }
    public void setTotalCombinedWeight(double total) { totalCombinedWeight = total; }

    public String toString() {
        String boaterDay1Catch = "| ";
        String boaterDay1WeightString = "";
        if (boaterFishedDay1) {
            boaterDay1Catch += boaterDay1Fish + "(" + boaterDay1FishAlive + ")";
            boaterDay1WeightString = String.format("%,.2f", boaterDay1Weight);
        }
        String boaterDay2Catch = "";
        String boaterDay2WeightString = "";
        if (boaterFishedDay2) {
            boaterDay2Catch = "" + boaterDay2Fish + "(" + boaterDay2FishAlive + ")";
            boaterDay2WeightString = String.format("%,.2f", boaterDay2Weight);
        }

        String partner = "";
        String partnerDay1Fish = "| ";
        String partnerDay1BigBass = "";
        String partnerDay1Weight = "";
        String partnerDay2Fish = "";
        String partnerDay2BigBass = "";
        String partnerDay2Weight = "";
        if (coAngler != null) {
            partner = coAngler.getName();
            if (coAnglerFishedDay1) {
                partnerDay1Fish += coAnglerDay1Fish + "(" + coAnglerDay1FishAlive + ")";
                partnerDay1Weight += String.format("%.2f", coAnglerDay1Weight);
                partnerDay1BigBass = coAnglerDay1BigBass;
            }
            if (coAnglerFishedDay2) {
                partnerDay2Fish += coAnglerDay2Fish + "(" + coAnglerDay2FishAlive + ")";
                partnerDay2BigBass = coAnglerDay2BigBass;
                partnerDay2Weight += String.format("%.2f", coAnglerDay2Weight);
            }
        }

        return String.format("%3d%3s%-18s%-6s%6s%9s%3s%-5s%5s%9s%-3s%-18s%-6s%6s%9s%3s%-5s%5s%9s%2s%6.2f",
                place, "",
                boater.getName(),
                boaterDay1Catch,
                boaterDay1BigBass,
                boaterDay1WeightString, " | ",
                boaterDay2Catch,
                boaterDay2BigBass,
                boaterDay2WeightString, " |",
                partner,
                partnerDay1Fish,
                partnerDay1BigBass,
                partnerDay1Weight, " | ",
                partnerDay2Fish,
                partnerDay2BigBass,
                partnerDay2Weight, " |",
                totalCombinedWeight
        );
    }

    public String toHTMLString() {
        String boaterDay1Catch = "";
        String boaterDay1WeightString = "";
        if (boaterFishedDay1) {
            boaterDay1Catch += boaterDay1Fish + "(" + boaterDay1FishAlive + ")";
            boaterDay1WeightString = String.format("%.2f", boaterDay1Weight);
        }
        String boaterDay2Catch = "";
        String boaterDay2WeightString = "";
        if (boaterFishedDay2) {
            boaterDay2Catch = "" + boaterDay2Fish + "(" + boaterDay2FishAlive + ")";
            boaterDay2WeightString = String.format("%.2f", boaterDay2Weight);
        }

        String partner = "";
        String partnerDay1Fish = "";
        String partnerDay1BigBass = "";
        String partnerDay1Weight = "";
        String partnerDay2Fish = "";
        String partnerDay2BigBass = "";
        String partnerDay2Weight = "";
        if (coAngler != null) {
            partner = coAngler.getName();
            if (coAnglerFishedDay1) {
                partnerDay1Fish += coAnglerDay1Fish + "(" + coAnglerDay1FishAlive + ")";
                partnerDay1Weight += String.format("%.2f", coAnglerDay1Weight);
                partnerDay1BigBass = coAnglerDay1BigBass;
            }
            if (coAnglerFishedDay2) {
                partnerDay2Fish += coAnglerDay2Fish + "(" + coAnglerDay2FishAlive + ")";
                partnerDay2BigBass = coAnglerDay2BigBass;
                partnerDay2Weight += String.format("%.2f", coAnglerDay2Weight);
            }
        }
        String str = "<tr>";
        str += "<td style=\"text-align: center\">" + place + "</td>"
                + "<td>" + boater.getName() + "</td>"
                + "<td style=\"text-align: center; border-left: 1px dashed gray;\">" + boaterDay1Catch + "</td>"
                + "<td style=\"text-align: right\">" + boaterDay1BigBass + "</td>"
                + "<td style=\"text-align: right\">" + boaterDay1WeightString + "</td>"
                + "<td style=\"text-align: center; border-left: 1px dashed gray;\">" + boaterDay2Catch + "</td>"
                + "<td style=\"text-align: right\">" + boaterDay2BigBass + "</td>"
                + "<td style=\"text-align: right\">" + boaterDay2WeightString + "</td>"
                + "<td style=\"border-left: 1px dashed gray; padding-left: 5px\">" + partner + "</td>"
                + "<td style=\"text-align: center; border-left: 1px dashed gray;\">" + partnerDay1Fish + "</td>"
                + "<td style=\"text-align: right\">" + partnerDay1BigBass + "</td>"
                + "<td style=\"text-align: right\">" + partnerDay1Weight + "</td>"
                + "<td style=\"text-align: center; border-left: 1px dashed gray;\">" + partnerDay2Fish + "</td>"
                + "<td style=\"text-align: right\">" + partnerDay2BigBass + "</td>"
                + "<td style=\"text-align: right\">" + partnerDay2Weight + "</td>"
                + "<td style=\"text-align: right; border-left: 1px dashed gray;\">" + String.format("%.2f",totalCombinedWeight) + "</td>";
        str += "</tr>";
        return str;
    }
}