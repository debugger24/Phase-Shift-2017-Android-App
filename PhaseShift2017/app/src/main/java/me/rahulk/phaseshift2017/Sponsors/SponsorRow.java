package me.rahulk.phaseshift2017.Sponsors;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by debugger24 on 13/09/17.
 */

public class SponsorRow {
    private List<Sponsor> sponsorRow;
    private String type;

    public SponsorRow(String type) {
        sponsorRow = new ArrayList<Sponsor>();
        this.type = type;
    }

    public void addSponsor(Sponsor sponsor) {
        sponsorRow.add(sponsor);
    }

    public String getType() {
        return type;
    }

    public List<Sponsor> getSponsorRow() {
        return sponsorRow;
    }
}
