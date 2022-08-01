import { PrimaryButton, SecondaryButton } from "@fremtind/jkl-button-react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@fremtind/jkl-table-react";
import { InfoTag, Tag } from "@fremtind/jkl-tag-react";
import { useState } from "react";
import { AiOutlineDown, AiOutlineUp } from "react-icons/ai";
import { BsChat } from "react-icons/bs";
import { useRequest } from "../../common/useRequest";
import { CheckFlere } from "./CheckFlere";
import { MoreButton } from "./MoreButton";
import { TbCheckBox } from "./TbCheckBox";
import "./TbTable.scss";

interface Tag {
  id: number;
  navn: string;
}

type SortKey = keyof tbData;
export type tbData = {
  id: string;
  dato: Date;
  kilde: string;
  sted: string;
  tilbakemeldingstekst: string;
  score: string;
  kontekstNummer: string;
  distributoer: string;
  tags: Tag[];
  kommentar: string;
};

export const TbTable = () => {
  const { table, isLoading } = useRequest();

  const [check, setCheck] = useState<Array<string>>([]);
  function handelCheckChange(checked: boolean, id: string) {
    if (checked) {
      setCheck([...check, id]);
    } else {
      const newIds = check.filter((ide) => ide !== id);
      setCheck(newIds);
    }
  }

  const headers: { key: SortKey; label: string }[] = [
    { key: "id", label: "Velg" },
    { key: "kontekstNummer", label: "Org.nr" },
    { key: "tilbakemeldingstekst", label: "Tilbakemelding" },
    { key: "score", label: "Score" },
    { key: "dato", label: "Dato" },
    { key: "sted", label: "Sted" },
    { key: "distributoer", label: "Distributør" },
    { key: "kommentar", label: "" },
    { key: "tags", label: "Tags" },
  ];

  const [sort, setSort] = useState<SortKey>();
  const handleSort = (key: SortKey) => {
    //const keyName = setSort(key);
    return setSort(key);
  };

  const [showMenu, setShowMenu] = useState(false);
  const handleTagClick = () => {
    setShowMenu(!showMenu);
  };

  return (
    <main className="body">
      <div className="export-content">
        <PrimaryButton
          className="legg-til-tags"
          type="submit"
          onClick={handleTagClick}
        >
          Tag markerte
        </PrimaryButton>{" "}
        {showMenu && <CheckFlere id={check}></CheckFlere>}
        <SecondaryButton className="button">
          Eksporter til Excel
        </SecondaryButton>
      </div>
      <Table className="table">
        <TableHead>
          <TableRow>
            {headers.map((row) =>(
                <TableHeader key={row.key}>
                  {/* To do !!! 
                  sortering funksjon må fikses*/}
                  {row.label}{" "}
                  <button
                    className="sort"
                    onClick={() => {
                      const sortElement = row.key;
                      handleSort(sortElement);
                      console.log(sortElement);
                    }}
                  >
                    {" "}
                    {row.label === "Tilbakemelding" ? (
                      !sort ? (
                        <AiOutlineUp />
                      ) : (
                        <AiOutlineDown />
                      )
                    ) : (
                      ""
                    )}
                    {row.label === "Dato" ? (
                      !sort ? (
                        <AiOutlineUp />
                      ) : (
                        <AiOutlineDown />
                      )
                    ) : (
                      ""
                    )}
                    {row.label === "Score" ? (
                      !sort ? (
                        <AiOutlineUp />
                      ) : (
                        <AiOutlineDown />
                      )
                    ) : (
                      ""
                    )}
                  </button>
                </TableHeader>
              )
            )}
          </TableRow>
        </TableHead>
        <TableBody>
          {Array.isArray(table)
            ? table.map((row: tbData) => {
                return (
                  <TableRow key={row.id}>
                    <TableCell>
                      {" "}
                      <TbCheckBox
                        id={row.id}
                        checked={check.includes(row.id)}
                        onChange={(checked) => {
                          handelCheckChange(checked, row.id);
                        }}
                      />
                    </TableCell>
                    <TableCell>{row.kontekstNummer}</TableCell>
                    <TableCell>{row.tilbakemeldingstekst}</TableCell>
                    <TableCell>{row.score}</TableCell>
                    <TableCell>
                      {new Date(row.dato).toLocaleDateString()}
                    </TableCell>
                    <TableCell>{row.sted}</TableCell>
                    <TableCell>{row.distributoer}</TableCell>
                    <TableCell
                      className={row.kommentar ? "chatKomment" : ""}
                      data-tooltip={row.kommentar}
                    >
                      {row.kommentar ? <BsChat /> : ""}
                    </TableCell>
                    <TableCell>
                      {row.tags.map((tag) => (
                        <InfoTag className="jkl-spacing-2xs--right jkl-spacing-2xs--top">
                          {tag.navn}
                        </InfoTag>
                      ))}
                    </TableCell>
                    <TableCell>
                      <MoreButton id={row.id} />
                    </TableCell>
                  </TableRow>
                );
              })
            : null}
        </TableBody>
      </Table>
    </main>
  );
};
