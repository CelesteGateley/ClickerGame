package clickergame;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("unused")
public class Upgrade {

    private static final Font LARGE = new Font("default", Font.PLAIN, 30);
    private static final Font MEDIUM = new Font("default", Font.PLAIN, 20);
    private static final Font SMALL = new Font("default", Font.PLAIN, 12);

    private String rawName;
    private String rawPlural;
    private String name;
    private String plural;

    private long amount;
    private long cost;
    private long baseCost;
    private double clicksPerSecond;

    private JButton buyButton = new JButton("Buy");
    private JLabel infoLabel = new JLabel("", JLabel.CENTER);
    private JLabel priceLabel = new JLabel("", JLabel.CENTER);
    private JLabel amountLabel = new JLabel("", JLabel.CENTER);

    Upgrade(String rawName, String rawPlural, long baseCost, double clicksPerSecond, Game instance){
        this.amount = 0;
        this.cost = baseCost;
        this.baseCost = baseCost;
        this.rawName = rawName;
        this.clicksPerSecond = clicksPerSecond;
        this.rawPlural = rawPlural;
        this.name = rawName.substring(0,1).toUpperCase() + rawName.substring(1);
        this.plural = rawPlural.substring(0,1).toUpperCase() + rawPlural.substring(1);

        this.initialiseSwing(instance);

    }

    Upgrade(String rawName, long baseCost, double clicksPerSecond, Game instance) {
        this.amount = 0;
        this.cost = baseCost;
        this.baseCost = baseCost;
        this.rawName = rawName;
        this.clicksPerSecond = clicksPerSecond;
        this.rawPlural = rawName + "s";
        this.name = rawName.substring(0,1).toUpperCase() + rawName.substring(1);
        this.plural = rawPlural.substring(0,1).toUpperCase() + rawPlural.substring(1);

        this.initialiseSwing(instance);

    }

    private void initialiseSwing(Game instance) {
        this.buyButton.addActionListener(new ButtonClickListener(instance));
        this.buyButton.setActionCommand("buy-" + this.rawName);
        this.buyButton.setFont(SMALL);

        this.infoLabel.setText(this.getInfoMessage());
        this.infoLabel.setFont(SMALL);

        this.priceLabel.setText(this.getPriceMessage());
        this.priceLabel.setFont(SMALL);

        this.amountLabel.setText(this.getAmountMessage());
        this.amountLabel.setFont(SMALL);
    }

    long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(long baseCost) {
        this.baseCost = baseCost;
    }

    public double getClicksPerSecond() {
        return clicksPerSecond;
    }

    public void setClicksPerSecond(long clicksPerSecond) {
        this.clicksPerSecond = clicksPerSecond;
    }

    public String getPlural() {
        return plural;
    }

    public void setPlural(String plural) {
        this.plural = plural;
    }

    public String getPluralRaw() {
        return rawPlural;
    }

    public void setPluralRaw(String pluralRaw) {
        this.rawPlural = pluralRaw;
    }

    String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    private String getAmountMessage() {
        return String.format("<html><center>Amount:<br />%d</center></html>", this.amount);
    }

    private String getPriceMessage() {
        return String.format("<html><center>Price:<br />%d</center></html>", this.cost);
    }

    private String getInfoMessage() {
        if (this.clicksPerSecond == 1) {
            return this.name + ": Produces " + this.clicksPerSecond + " Click Per Second";
        } else {
            return this.name + ": Produces " + this.clicksPerSecond + " Clicks Per Second";
        }
    }

    void purchase() {
        this.amount++;
        this.cost = (long) (baseCost * (Math.pow(1.15, this.amount)));
    }

    double getClicks() {
        return this.amount * this.clicksPerSecond;
    }

    void updateLabels() {
        this.infoLabel.setText(this.getInfoMessage());
        this.amountLabel.setText(this.getAmountMessage());
        this.priceLabel.setText(this.getPriceMessage());
    }

    JPanel getUpgradeSection() {
        this.updateLabels();

        JPanel section = new JPanel(new GridLayout(2,1));
        JPanel buttons = new JPanel(new GridLayout(1,3));

        section.add(this.infoLabel);
        buttons.add(this.buyButton);
        buttons.add(this.priceLabel);
        buttons.add(this.amountLabel);

        section.add(buttons);

        return section;

    }

}
