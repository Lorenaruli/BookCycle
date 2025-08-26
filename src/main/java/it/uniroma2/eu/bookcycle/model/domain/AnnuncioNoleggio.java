package it.uniroma2.eu.bookcycle.model.domain;

public class AnnuncioNoleggio extends Annuncio{
    private int numMesi;

    public AnnuncioNoleggio(Libro libro, double prezzo, int numMesi) {
        super(libro, prezzo);
        this.numMesi = numMesi;
    }
    @Override
    public TipoAnnuncio getTipo() {
        return TipoAnnuncio.ANNUNCIONOLEGGIO;
    }
}
