import { SecondaryButton } from "@fremtind/jkl-button-react";
import { Checkbox } from "@fremtind/jkl-checkbox-react";
import { DatePicker, formatInput } from "@fremtind/jkl-datepicker-react";
import { SuccessTag } from "@fremtind/jkl-tag-react";
import { TextInput } from "@fremtind/jkl-text-input-react";
import { useState } from "react";
import { usePost } from "../../common/useFetch";

interface Props {
  filter: Map<string, string[]>;
}

const usePostFilter = (filter: Map<string, string[]>) =>
  usePost(
    "http://localhost:8080//setAttributter",
    JSON.stringify(Object.fromEntries(filter))
  );

const handleSubmit = (
  map: Map<string, string[]>,
  key: string,
  value: string,
  setState: (s: string) => void,
  post: any
) => {
  const oldList = map.get(key) || [];
  map.set(key, oldList?.concat([value]));
  setState("");
  post.fetch(JSON.stringify(Object.fromEntries(map)));
};

const handleRemove = (
  map: Map<string, string[]>,
  key: string,
  val: string,
  post: any
) => {
  const oldList = map.get(key) || [];
  oldList.splice(oldList.indexOf(val), 1);
  map.get(key);
  post.fetch(JSON.stringify(Object.fromEntries(map)));
};

export const SearchOrgNrHeader = ({ filter }: Props) => {
  const [orgNummer, setOrgNummer] = useState("");
  const addOrgNummer = (e: React.ChangeEvent<HTMLInputElement>) => {
    setOrgNummer(e.target.value);
  };

  const post = usePostFilter(filter);

  const handelOrgNummer = () => {
    handleSubmit(filter, "kontekstNummer", orgNummer, setOrgNummer, post);
  };

  const fjern = (orgnr: string) => () => {
    handleRemove(filter, "kontekstNummer", orgnr, post);
  };

  return (
    <div>
      {filter.get("kontekstNummer")?.map((t) => (
        <SuccessTag
          className="jkl-spacing-2xs--right jkl-spacing-2xs--top"
          dismissAction={{
            label: "Fjern organisasjonsnummer",
            onClick: fjern(t),
          }}
        >
          {t}
        </SuccessTag>
      ))}
      <TextInput
        label="Søk på org.nummer"
        name="OrgNummer"
        key={"org"}
        forceCompact={false}
        variant={"small"}
        value={orgNummer}
        action={{
          icon: "clear",
          label: "Nullstill feltet",
          onClick: () => setOrgNummer(""),
        }}
        onChange={addOrgNummer}
      />
      <br />
      <SecondaryButton
        forceCompact={true}
        loader={{
          showLoader: false,
          textDescription: "Laster innhold",
        }}
        onClick={handelOrgNummer}
        className="jkl-spacing-l--right"
      >
        Legg til
      </SecondaryButton>
    </div>
  );
};

export const SearchTekstHeader = ({ filter }: Props) => {
  const [tekst, setTekst] = useState("");
  const addTekst = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTekst(e.target.value);
  };

  const post = usePostFilter(filter);

  const handelTekst = () => {
    handleSubmit(filter, "tilbakemeldingstekst", tekst, setTekst, post);
  };

  const fjern = (tekst: string) => () => {
    handleRemove(filter, "tilbakemeldingstekst", tekst, post);
  };

  return (
    <div>
      {filter.get("tilbakemeldingstekst")?.map((t) => (
        <SuccessTag
          className="jkl-spacing-2xs--right jkl-spacing-2xs--top"
          dismissAction={{
            label: "Fjern tekst",
            onClick: fjern(t),
          }}
        >
          {t}
        </SuccessTag>
      ))}
      <TextInput
        label="Søk på ord i tilbakemelding"
        name="Tekst"
        forceCompact={false}
        variant={"small"}
        value={tekst}
        action={{
          icon: "clear",
          label: "Nullstill feltet",
          onClick: () => setTekst(""),
        }}
        onChange={addTekst}
      />
      <br />
      <SecondaryButton
        forceCompact={true}
        loader={{
          showLoader: false,
          textDescription: "Laster innhold",
        }}
        onClick={handelTekst}
        className="jkl-spacing-l--right"
      >
        Legg til
      </SecondaryButton>
    </div>
  );
};
export const SearchStedHeader = ({ filter }: Props) => {
  const [sted, setSted] = useState("");
  const addSted = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSted(e.target.value);
  };

  const post = usePostFilter(filter);
  const handelSted = () => {
    handleSubmit(filter, "sted", sted, setSted, post);
  };

  const fjern = (tekst: string) => () => {
    handleRemove(filter, "sted", tekst, post);
  };

  return (
    <div>
      {filter.get("sted")?.map((t) => (
        <SuccessTag
          className="jkl-spacing-2xs--right jkl-spacing-2xs--top"
          dismissAction={{
            label: "Fjern sted",
            onClick: fjern(t),
          }}
        >
          {t}
        </SuccessTag>
      ))}
      <TextInput
        label="Velg sted"
        name="Tekst"
        forceCompact={false}
        variant={"small"}
        value={sted}
        action={{
          icon: "clear",
          label: "Nullstill feltet",
          onClick: () => setSted(""),
        }}
        onChange={addSted}
      />
      <br />
      <SecondaryButton
        forceCompact={true}
        loader={{
          showLoader: false,
          textDescription: "Laster innhold",
        }}
        onClick={handelSted}
        className="jkl-spacing-l--right"
      >
        Legg til
      </SecondaryButton>
    </div>
  );
};

export const ScoreFilter = ({ filter }: Props) => {
  const headers: string[] = ["1", "2", "3", "4", "5"];

  const post = usePostFilter(filter);

  const addScore = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.checked) {
      handleSubmit(filter, "score", e.target.value, () => null, post);
    } else {
      handleRemove(filter, "score", e.target.value, post);
    }
  };

  return (
    <div>
      {headers.map((val) => {
        return (
          <Checkbox
            name="checklist"
            checked={!!filter.get("score")?.find((e) => e === val)}
            value={val}
            onChange={addScore}
          >
            {val}
          </Checkbox>
        );
      })}
    </div>
  );
};

export const VelgDistribtor = ({ filter }: Props) => {
  const headers: string[] = ["DNB", "SB1", "Fremtind"];

  const post = usePostFilter(filter);

  const addScore = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.checked) {
      handleSubmit(filter, "distributoer", e.target.value, () => null, post);
    } else {
      handleRemove(filter, "distributoer", e.target.value, post);
    }
  };

  return (
    <div>
      {headers.map((val) => {
        return (
          <Checkbox
            name="checklist"
            checked={!!filter.get("distributoer")?.find((e) => e === val)}
            value={val}
            onChange={addScore}
          >
            {val}
          </Checkbox>
        );
      })}
    </div>
  );
};

export const VelgDato = ({ filter }: Props) => {
  const post = usePostFilter(filter);

  const addFraDato = (e: React.ChangeEvent<HTMLInputElement>) => {
    handleSubmit(filter, "dato", e.target.value, () => null, post);
  };
  const addTilDato = (e: React.ChangeEvent<HTMLInputElement>) => {
    handleSubmit(filter, "dato", e.target.value, () => null, post);
  };

  return (
    <div className="dato">
      <div className="fra">
        <DatePicker
          className={"jkl-spacing-l--bottom"}
          label="Fra "
          disableAfterDate={formatInput(new Date())}
          onChange={addFraDato}
        />
      </div>
      <div className="til">
        <DatePicker
          className={"jkl-spacing-l--bottom"}
          label="Til "
          onChange={addTilDato}
        />
      </div>
    </div>
  );
};
