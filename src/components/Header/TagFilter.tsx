import { SecondaryButton } from "@fremtind/jkl-button-react";
import { Select } from "@fremtind/jkl-select-react";
import { TextInput } from "@fremtind/jkl-text-input-react";
import { useEffect, useState } from "react";
import { usePost } from "../../common/useFetch";

type Tag = {
  id: number;
  navn: string;
};
// To do, filtrernig etter tag er ikke klar enda !!
export const Tagfilter = () => {
  const [tagId, setTagId] = useState<string[]>([]);
  const [selectedTag, setSelectedTag] = useState<String>();
  const [result, setResult] = useState<Tag[]>([]);

  useEffect(() => {
    const api = async () => {
      const data = await fetch("http://localhost:8080//getAllTags", {
        method: "GET",
      });
      const jsonData = await data.json();
      setResult(jsonData);
    };
    api();
  }, []);

  const [moreTag, setMoreTag] = useState("");
  const addTag = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMoreTag(e.target.value);
  };

  const postTag = usePost(
    "http://localhost:8080//addTagtoTilbakemeldinger",
    JSON.stringify({ idTilbakemeldinger: tagId, tag: moreTag })
  );

  const handleSaveTag = () => {
    postTag.fetch();
    setMoreTag("");
  };
  return (
    <div>
      <div>
        <Select
          className="jkl-spacing-xl--bottom"
          errorLabel={undefined}
          searchable={true}
          onChange={(e) => {
            setSelectedTag(e.target.value);
            // setTagId([id]);
          }}
          items={result.map((row) => ({
            label: `${row.navn}`,
            value: row.navn,
          }))}
          label="Søk på tag"
          name={""}
        />
      </div>
      <div>
        <TextInput
          label="Legg til en ny tag "
          name="OrgNummer"
          key={"org"}
          forceCompact={false}
          variant={"small"}
          value={moreTag}
          action={{
            icon: "clear",
            label: "Nullstill feltet",
            onClick: () => setMoreTag(""),
          }}
          onChange={addTag}
        />
        <br />
        <SecondaryButton
          forceCompact={true}
          loader={{
            showLoader: false,
            textDescription: "Laster innhold",
          }}
          onClick={handleSaveTag}
          className="jkl-spacing-l--right"
        >
          Legg til
        </SecondaryButton>
      </div>
    </div>
  );
};
