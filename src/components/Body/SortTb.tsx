import { MouseEventHandler, useState } from "react";
import { AiOutlineDown, AiOutlineUp } from "react-icons/ai";

// To Do. sorting funksjon må fikses
export function SortTb(key: any) {
  const [sort, setSort] = useState<any>();
  const handleSort = (key: any) => {
    //const keyName = setSort(key);
    console.log(key);
    return setSort(key);
  };
  //Dette er en funksjon for å sortere i stygende og synkende rekkefølge
  function SortButton({
    // sortKey,
    columnKey,
    onClick,
  }: {
    //sortKey: SortKey;
    columnKey: any;
    onClick: MouseEventHandler<HTMLButtonElement>;
  }) {
    return (
      <button className={"sort"} onClick={onClick}>
        {" "}
        {!sort ? <AiOutlineUp /> : <AiOutlineDown />}{" "}
      </button>
    );
  }
  return <></>;
}
