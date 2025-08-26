package it.uniroma2.eu.bookcycle.model.domain;

public class AnnuncioVendita extends Annuncio{

    public AnnuncioVendita(Libro libro, double prezzo) {
        super(libro, prezzo);
    }
    @Override
    public TipoAnnuncio getTipo() {
        return TipoAnnuncio.ANNUNCIOVENDITA;
    }
}
