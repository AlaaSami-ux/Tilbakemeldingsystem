import { useRef, useState } from "react";
import { ExpandButton } from "@fremtind/jkl-expand-button-react";
import { useClickOutside } from "@fremtind/jkl-react-hooks";
import { Checkbox } from "@fremtind/jkl-checkbox-react";

export const Attrebut=()=> {
  const [isExpanded, setIsExpanded] = useState(false);
  const expandButtonId = "jkl-example-expand-button";
  const expandedContentId = "jkl-example-expand-content";

  const clickOutsideRef = useRef(null);

  const onClickOutside = () => {
    if (isExpanded) {
      setIsExpanded(false);
    }
  };

  useClickOutside(clickOutsideRef, onClickOutside);

  const onClick = () => {
    setIsExpanded(!isExpanded);
  };

  return (
    <>
      <div className="dropdown" ref={clickOutsideRef}>
        <ExpandButton
          aria-controls={expandedContentId}
          id={expandButtonId}
          className="dropdown__controller"
          forceCompact={false}
          isExpanded={isExpanded}
          onClick={onClick}
        >
          {isExpanded ? "Skjul kolonner" : "Velg kolonner"}
        </ExpandButton>

        <div
          aria-labelledby={expandButtonId}
          id={expandedContentId}
          role="group"
          hidden={!isExpanded}
          className="dropdown__content"
        >
          <div className="jkl-expand-section__content">
            <form>
              <Checkbox
                name="checklist"
                value="org"
                invalid={false}
                forceCompact={false}
              >
                Org.nummer
              </Checkbox>
              <Checkbox
                name="checklist"
                value="tilbakemelding"
                invalid={false}
                forceCompact={false}
              >
                Tilbakemelding
              </Checkbox>
              <Checkbox
                name="checklist"
                value="score"
                invalid={false}
                forceCompact={false}
              >
                Score
              </Checkbox>
              <Checkbox
                name="checklist"
                value="dato"
                invalid={false}
                forceCompact={false}
              >
                Dato
              </Checkbox>
              <Checkbox
                name="checklist"
                value="sted"
                invalid={false}
                forceCompact={false}
              >
                Sted
              </Checkbox>
              <Checkbox
                name="checklist"
                value="distributør"
                invalid={false}
                forceCompact={false}
              >
                Distributør
              </Checkbox>
              <Checkbox
                name="checklist"
                value="tags"
                invalid={false}
                forceCompact={false}
              >
                Tags
              </Checkbox>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}
