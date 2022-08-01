import { Checkbox } from "@fremtind/jkl-checkbox-react";
interface Props {
  id: string;
  checked: boolean;
  onChange: (checcked: boolean) => void;
}
export function TbCheckBox(props: Props) {
  //Denne funksjon vil bli utløst når en checkbox bytte sin status
  const selectTb = (event: React.ChangeEvent<HTMLInputElement>) => {
    props.onChange(event.target.checked);
  };
  return (
    <>
      <Checkbox
        name="checklist"
        invalid={false}
        forceCompact={false}
        children={undefined}
        value={props.id}
        checked={props.checked}
        onChange={selectTb}
      ></Checkbox>
    </>
  );
}
