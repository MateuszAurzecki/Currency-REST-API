package pl.b2b.Nbp.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.b2b.Nbp.model.Calculator;
import pl.b2b.Nbp.model.CurrencyWithDatesObject;
import pl.b2b.Nbp.model.GoldRateObject;
import pl.b2b.Nbp.model.Rates;
import pl.b2b.Nbp.service.NbpApiService;

import java.util.List;

@ApiModel
@RestController
public class NbpController {


    @Autowired
    private NbpApiService nbpApiService;

    //funcja zwraca aktualny sredni kurs podanej waluty

    @GetMapping("/code/{code}")
    public Rates getSingleCurrencyMid(@ApiParam(defaultValue = "AUD",name = "code",value = "AUD") @PathVariable String code) {
        Rates result = nbpApiService.getSingleCurrency(code);
        return result;
    }

    //funkcja pomocnicza zwracjaca aktualne notowania wszystkich walut z tabeli a

    @GetMapping("/allCurrenciesMid")
    public List<Rates> getCurrenciesMid() {
        List<Rates> result = nbpApiService.getAllCurrenciesMid();
        return result;
    }

    //funkcja zraca sredia wartosc danej waluty w przedziale czasowym oraz notowania tej waluty w poszczegolnych dniach
    //zakres dat nie moze byc wiekszy niz 92 dni

    @GetMapping("currency/{code}/{start}/{end}")
    public CurrencyWithDatesObject getCurrencyWithSpecuficDate(@PathVariable String code, @PathVariable String start, @PathVariable String end) {

        CurrencyWithDatesObject result = nbpApiService.getSingleCurrencyInSpecificDates(code, start, end);
        return result;
    }

    //funkcja oblicza wartosc posiadancyh jednostek danej waluty po aktualnym kursie

    @GetMapping("value/{code}/{units}")
    public Calculator CurrencyCalculator(@PathVariable String code, @PathVariable Double units) {
        return nbpApiService.currencyCalculator(code, units);
    }


    //funkcja obliczajaca wartosc jednostek posiadanaego zlota w podanym dniu

    @GetMapping("/gold/{data}/{units}")
    public GoldRateObject goldPriceInSpecificDate(@PathVariable String data, @PathVariable Double units) {
        return nbpApiService.goldRateInSpecificDate(data, units);
    }
}
