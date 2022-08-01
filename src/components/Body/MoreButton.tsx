//MoreButton er en knap for å legge til tags og kommentarer
import { TextInput } from "@fremtind/jkl-text-input-react";
import { SecondaryButton } from "@fremtind/jkl-button-react";
import { Accordion, AccordionItem } from "@fremtind/jkl-accordion-react";
import { UnorderedList } from "@fremtind/jkl-list-react";
import { useEffect, useRef, useState } from "react";
import { usePost } from "../../common/useFetch";
import { Select } from "@fremtind/jkl-select-react";

interface Props {
  id: string;
}

export function MoreButton(props: Props) {
  const [showMenu, setShowMenu] = useState(false);
  const handleOpen = () => {
    setShowMenu(!showMenu); // Utløse Accordion
  };

  const [tagId, setTagId] = useState<string[]>([]);
  const [selectedTag, setSelectedTag] = useState<String>();

  const handleTagClick = () => {
    postTag.fetch();
    setShowMenu(!showMenu);
  };

  const postTag = usePost(
    "http://localhost:8080//addTagtoTilbakemeldinger",
    JSON.stringify({ idTilbakemeldinger: tagId, tag: selectedTag })
  );

  const [komt, setKommentar] = useState("");
  const addKommentar = (e: React.ChangeEvent<HTMLInputElement>) => {
    setKommentar(e.target.value);
  };

  const postKommentart = usePost(
    "http://localhost:8080//addKommentarToTilbakemelding",
    JSON.stringify({ tilbakemeldingId: props.id.toString(), kommentar: komt })
  );

  const handleSaveComent = () => {
    postKommentart.fetch();
    setShowMenu(!showMenu);
  };

  const dropdownRef = useRef<HTMLDivElement>(null);

  const handleClickOutsideDropdown = (e: any) => {
    if (showMenu && !dropdownRef.current?.contains(e.target as Node)) {
      setShowMenu(false);
    }
  };

  window.addEventListener("click", handleClickOutsideDropdown);

  type Tag = {
    id: number;
    navn: string;
  };

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

  return (
    <div className="legg-til-container" ref={dropdownRef}>
      <SecondaryButton className="leggTil" onClick={handleOpen}>
        legg til
        {/* <div>{showMenu}</div> */}
      </SecondaryButton>
      <Accordion className="more-popup-box">
        {showMenu && (
          <div className="more-tag-popup">
            <button className="close-icon" onClick={handleOpen}>
              X
            </button>
            <AccordionItem
              className={"kommentar-accordion"}
              title="Legg til kommetar"
            >
              <UnorderedList>
                <TextInput
                  label="Kommentar"
                  name="kommentar"
                  forceCompact={false}
                  variant={"small"}
                  value={komt}
                  onChange={addKommentar}
                />
                <br />
                <SecondaryButton
                  forceCompact={true}
                  loader={{
                    showLoader: false,
                    textDescription: "Laster innhold",
                  }}
                  onClick={handleSaveComent}
                  className="jkl-spacing-l--right"
                >
                  Lagre
                </SecondaryButton>
              </UnorderedList>
            </AccordionItem>
            <AccordionItem className={"tag-accordion"} title="Legg til tag">
              <Select
                className="jkl-spacing-xl--bottom"
                errorLabel={undefined}
                searchable={true}
                onChange={(e) => {
                  setSelectedTag(e.target.value);
                  setTagId([props.id]);
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
        )}
      </Accordion>
    </div>
  );
}
