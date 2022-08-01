package no.fremtind.feedbackhandler;

import lombok.extern.slf4j.Slf4j;
import no.fremtind.feedbackhandler.controller.HenteManager;
import no.fremtind.feedbackhandler.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.aspectj.bridge.Version.getTime;

@Slf4j
@SpringBootApplication
public class FeedbackHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedbackHandlerApplication.class, args);
	}


	@Bean
	public CommandLineRunner demo(TilbakemeldingRepository tbRepo, TagRepository tagRepo) {
		return (args) -> {

			//Tester tilbakemeldingsfunksjonaliteten
			HenteManager hm = new HenteManager();
			hm.tilbakemeldingRepository = tbRepo;
			hm.tagRepository = tagRepo;
			//hm.setTest();

			Tilbakemelding tbTest = new Tilbakemelding("BM","Dette er en tilbakemelding som testes", 4,"Fremtind");
			Tag tagTest = new Tag(null, "Design");

			tagRepo.save(new Tag(null, "Reise"));
			tagRepo.save(new Tag(null, "Test"));
			tagRepo.save(new Tag(null, "QA"));
			tagRepo.save(tagTest);


			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 3, 12345675, "Kunne kanskje ha fått beskjed om egenandelen før jeg sendte inn saken. Hvis jeg ikke hadde hatt helkundefordelen med redusert egenandel så kan det hende jeg heller hadde valgt å bytte skjerm selv uoriginalt for 1500kr istedet for å betale 2000kr i egenandel (men siden iPaden var såpass ny hadde jeg nok kanskje ikke gjort det i dette tilfellet).", "SB1","Faktura", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 5, 4545678, "Jeg er veldig fornøyd med behandling av saken. Tydelig og presis kommunikasjon og rask tilbakemelding gjennom hele perioden.", "DNB","dokumenter", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 1, 756745657, "Forsikringen min var plutselig endret uten at dette var kommunisert adekvat fra deres side. Dette gjør at jeg ikke kan bytte forsikringsselskap for aktuelle saken, da dette toget er gått. Hadde jeg fått tydelig beskjed, hadde jeg selvfølgelig byttet selskap før dere endret vilkårene.", "DNB","tilganger", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 2, 33345679, "Hadde forventet at skader på min sykkel blir dekket når jeg blir påkjørt av en annen syklist. Dette dekkes av reiseforsikring i annet forsikringsselskap. Trodde jeg hadde en av markedets beste forsikringer, men så feil kan man ta. Vurderer å bytte tilbake.", "DNB","tilganger-andre-bedrifter\n" +
					"\n", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 2, 12345675, "Som Lo-medlem, og enslig beboer, på snart 56 år, uten noensinne tidligere benyttet forsikring. Så blir jeg avkrevd egenandel. Der det i boenhet er flere medlemmer, er det ingen egenandel. Dette tross at mange \"notorisk \", jevnlig benytter forsikring. Føler at de på et vis \"belønnes\" da forsikring kan benyttes på \"filleting\" siden det ikke kreves egenandel . Å slike som meg som \"aldri\" benytter forsikring, å om det ved unntak benyttes forsikring, blir det avkrevd egenandel. Dette er noe LO kunne gjøre noe med ved avtaleinngåelse.", "SB1","Ansatt", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 4, 347834564, "Sikre faglig kompetanse slik at kunde/dyreeier ikke risikerer å bli nødt til å betale flere egenandeler pga feil vurdering fra saksbehandler. I tillegg bli bedre på oppfølging og ikke la det gå over en uke, og først svare når dere blir purret på.", "DNB","reelle-rettighetshavere", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 4, 123123123, "Vil gjerne ha kvittering på ansatte som blir meldt ut og inn av forsikringer og pensjon", "DNB","Faktura", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 1, 12345675, "DET ER JO INGEN TING SOM FUNGERER, ELENDIG !", "DNB","dokumenter", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 1, 878546878, "Helt elendig, vanskelig og finne i nettbank. Mangelfull info. Og det et kjøretøy EA 1319 som er solgt og det er ingen plass en kan stoppe forsikringen. ", "SB1","tilganger", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 5, 565656444, "Kan ikke si opp/endre forsikringen her, eller sende melding til dere herifra?", "DNB","tilganger-andre-bedrifter\n" +
					"\n", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 2, 234222499, "Finner ikke hvor jeg kan justere årsverk antall for yrkesskadeforsikringen..", "DNB","Ansatt", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 4, 347834564, "Savner chat funksjonen og å kunne søke i ansatte", "DNB","reelle-rettighetshavere", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 5, 12345675, "Veldig god behandling, gikk veldig fort! Enkelt og greit med en sms med kort forklaring og info!", "SB1","Faktura", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 5, 333746222, "Uventet rask og effektiv saksbehandling. Veldig bra å få tilbakemelding umiddelbart. Tusen takk!", "DNB","dokumenter", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 4, 222746398, "Veldig imøtekommende og hjelpsom kundebehandler. Føler meg godt ivaretatt. Takk for hjelp!", "DNB","tilganger", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 3, 465746666, "Takk rask behandling og oppgjør. Veldig fornøyd med den forsikringen jeg har med dere takker for det. S. Slyngstad", "DNB","tilganger-andre-bedrifter\n" +
					"\n", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 2, 982553398, "Ødelagt fryser, råtten mat, stinkende. Måtte fjerne både mat og fryser selv. Fryser kunne deponeres på fyllplass, «våtorganisk materiale» ble ikke akseptert, dette måtte deponeres som spesialavfall. Fremtinds aksbehandler jeg snakket med på telefonen var ikke interessert i å få detaljer på innhold «for dette har dere tabeller på» Jeg sendte allikevel inn bilder av innhold. Hadde en tilsvarende sak for 3 år siden (pga Brann hos naboen under og strømmen ble derfor slått av i 3 uker). Selskapet Gjensidige kom med mannskap, de hadde med vernemaske til meg for bruk da jeg skulle bistå med å identifisere innholdet. Mannskapet til Gjensidige fjernet ALT og det kom ett snarlig og rettferdig oppgjør. Fremtind fremstår som tiltaksløse, ikke service vennlige og ikke løsningsorienterte. Å bli overlatt til seg selv med stinkende avfall og å finne ut av sted for å deponere, innkjøp av plast sekker, kjøring til og fra med vinduene i bilen åpne pga stank er ikke mindre enn uakseptabelt. Dette er veldig smålig av Fremtind. Videre finner jeg det svært usmakelig at dere har lenke direkte til nettbutikk og skaper ett kunstig kjøpepress til bruk av oppgjøret. Dere fremstår som kyniske og ekstremt profitt hungrige. Jeg er svært skuffet og håper jeg slipper å ha noe mer med dere å gjøre, selv tatt i betraktning av at oppgjøret kom rask.", "SB1","Ansatt", new ArrayList<>(), ""));
			tbRepo.save(new Tilbakemelding(null,"BM", Date.valueOf(LocalDate.now()), 3, 12345675, "Hadde vitne til hendelsen som ikke ble kontaktet. Når motpart nektet for følgene av skaden ble jeg konfrontert i stedet for vitnet. Jeg hadde opplyst at jeg ikke var tilstede( parkert bil) Det var unødvendig å kontakte meg når vitnet kunne bekrefte hendelsen", "SB1","reelle-rettighetshavere", new ArrayList<>(), ""));

			tbRepo.save(tbTest);
			List<Long> tbListe = new ArrayList<>();
			tbListe.add(tbTest.getId().longValue());









			log.info("---------query----------");
			for (Tilbakemelding tb : hm.getTilbakemeldinger()){
				log.info(tb.toString());
			}
			log.info("---------TagTest----------");
			hm.setTagToTilbakemeldinger(tagTest.getNavn(), tbListe);
			log.info("Tilbakemelding: "+hm.getTilbakemeldinger());
			hm.setTagToTilbakemeldinger("QA", tbListe);
			log.info("Tilbakemelding med ny tag"+hm.getTilbakemeldinger());
			//test tilbakemelding slutt

		};
	}



}
