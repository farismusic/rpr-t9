package ba.unsa.etf.rpr;

import java.sql.*;
import java.util.ArrayList;

import static sun.management.jmxremote.ConnectorBootstrap.initialize;

public class GeografijaDAO {
    private static GeografijaDAO instance = null;
    private Connection konekcija;
    private String url;
    private Statement stmt;
    private ResultSet result;
    private PreparedStatement ps;
    private PreparedStatement brisi;

    private static void initialize() throws SQLException {
        instance = new GeografijaDAO();
    }

    private GeografijaDAO() throws SQLException {
        konekcija = DriverManager.getConnection("jdbc:sqlite:baza");
        Statement stmt = konekcija.createStatement();
        //ps = konekcija.prepareStatement("SELECT g.naziv FROM drzava d, grad g WHERE d.glavni_grad = ?");
        //brisi = konekcija.prepareStatement("DELETE FROM drzava d, grad g WHERE drzava = ? AND d.id = g.drzava");
    }

    public static GeografijaDAO getInstance() throws SQLException {
        if (instance == null) initialize();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    Grad glavniGrad(String drzavica) throws SQLException, NullPointerException {
        //ps.setString(1, drzava);
        //result = ps.executeQuery();
        Statement stmt = konekcija.createStatement();
        String upit = "SELECT g.naziv FROM drzava d, grad g WHERE d.naziv = "+ "'" + drzavica + "'" +" AND g.id = d.glavni_grad";
        ResultSet result = stmt.executeQuery(upit);
        Grad grad1 = new Grad();
        if (!result.next()) return null;
        grad1.setNaziv(result.getString(1));
        return grad1;
    }

    void obrisiDrzavu(String drzavica) throws SQLException {
        Statement stmt = konekcija.createStatement();
        String upit = "DELETE FROM drzava d, grad g WHERE drzava = " + "'" + drzavica + "'" + "AND d.id = g.drzava";
        ResultSet result = stmt.executeQuery(upit);
    }

    ArrayList<Grad> gradovi() throws SQLException {
        ArrayList<Grad> gradovi = new ArrayList<>();
        Statement stmt = konekcija.createStatement();
        String upit = "SELECT naziv FROM grad";
        ResultSet result = stmt.executeQuery(upit);
        while(result.next()){
            Grad grad = new Grad();
            grad.setNaziv(result.getString(1));
            gradovi.add(grad);
        }
        return gradovi;
    }


    public void dodajDrzavu(Drzava bih) throws SQLException {
        Statement stmt = konekcija.createStatement();
        String upit = "insert into drzava (id, naziv, glavni_grad) values (" + bih.getId() + "," + " '" + bih.getNaziv() + "' ," + bih.getGlavniGrad().getId() + ")";
        ResultSet result = stmt.executeQuery(upit);
    }

    public void dodajGrad(Grad sarajevo) throws SQLException {
        Statement stmt = konekcija.createStatement();
        String upit = "INSERT INTO grad(id, naziv, broj_stanovnika, drzava) VALUES (" + sarajevo.getId() + "," + sarajevo.getNaziv() + "," + sarajevo.getBrojStanovnika() + "," + sarajevo.getDrzava().getId() + ")";
        ResultSet result = stmt.executeQuery(upit);
    }

    public Drzava nadjiDrzavu(String francuska) throws SQLException {
        Statement stmt = konekcija.createStatement();
        String upit = "SELECT * FROM drzava WHERE naziv = "+ "'" + francuska + "'";
        ResultSet result = stmt.executeQuery(upit);
        if(!result.next()) return null;
        Drzava drzava = new Drzava();
        drzava.setId(result.getInt(1));
        drzava.setNaziv(francuska);
        return drzava;
    }

    public void izmijeniGrad(Grad bech) throws SQLException {
        Statement stmt = konekcija.createStatement();
        String upit1 = "SELECT naziv FROM grad";
    }
}
