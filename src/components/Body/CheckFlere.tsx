import { AccordionItem } from "@fremtind/jkl-accordion-react";
import { SecondaryButton } from "@fremtind/jkl-button-react";
import { Select } from "@fremtind/jkl-select-react";
import { useEffect, useState } from "react";
import { usePost } from "../../common/useFetch";

interface Props {
  id: Array<string>;
}

export function CheckFlere(props: Props) {

  const [tagId, setTagId] = useState<string[]>([]);
  const [selectedTag, setSelectedTag] = useState<String>();

  type Tag = {
    id: number;
    navn: string;
  };

  const postTag = usePost(
    "http://localhost:8080/addTagtoTilbakemeldinger",
    JSON.stringify({ idTilbakemeldinger: tagId, tag: selectedTag })
  );

  const handleTagClick = () => {
    postTag.fetch();
  };

  const [result, setResult] = useState<Tag[]>([]);
  useEffect(() => {
    const api = async () => {
      const data = await fetch("http://localhost:8080/getAllTags", {
        method: "GET",
      });
      const jsonData = await data.json();
      setResult(jsonData);
    };
    api();
  }, []);

  const [isOpne, setIsOpne] = useState(true);
  const handleOpne = () => {
    setIsOpne(!isOpne);
  }; //To do -- Må fikses

  return (
    <div className="popup-box">
      <button className="close-icon" onClick={handleOpne}>
        X
      </button>
      <AccordionItem className={"tag-popup"} title="Legg til tag">
        <Select
          className="jkl-spacing-xl--bottom"
          errorLabel={undefined}
          searchable={true}
          onChange={(e) => {
            setSelectedTag(e.target.value);
            setTagId(props.id);
          }}
          items={result.map((row) => ({
            label: `${row.navn}`,
            value: row.navn,
          }))}
          label=""
          name={""}
        />
        <SecondaryButton
          forceCompact={true}
          loader={{
            showLoader: false,
            textDescription: "Laster innhold",
          }}
          onClick={handleTagClick}
          className="jkl-spacing-l--right"
        >
          Legg til
        </SecondaryButton>
      </AccordionItem>
    </div>
  );
}


