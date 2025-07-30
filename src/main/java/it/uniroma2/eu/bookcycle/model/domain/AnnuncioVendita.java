package it.uniroma2.eu.bookcycle.model.domain;

public class AnnuncioVendita extends Annuncio{

    public AnnuncioVendita(Libro libro, double prezzo, Libraio libraio) {
        super(libro, prezzo, libraio);
    }
    @Override
    public TipoAnnuncio getTipo() {
        return TipoAnnuncio.ANNUNCIOVENDITA;
    }
}
