import { Tabs, TabList, Tab, TabPanel } from "@fremtind/jkl-tabs-react";
import { NativeSelect } from "@fremtind/jkl-select-react";
import "./Header.scss";
import { Attrebut } from "./Attrebutt";
import {
  ScoreFilter,
  SearchOrgNrHeader,
  SearchStedHeader,
  SearchTekstHeader,
  VelgDato,
  VelgDistribtor,
} from "./SearchHeader";
import { Tagfilter } from "./TagFilter";

export const Header = () => {
  const filter = new Map();

  return (
    <header className="header">
      <div className="logoAndKilde">
        <div className="logo">{"Gnål"}</div>
        <div className="kilde">
          <NativeSelect
            className="tb-system"
            id="produsent"
            name="produsent"
            forceCompact={false}
            variant="small"
            label=""
            helpLabel={undefined}
            errorLabel={undefined}
            items={[
              { value: "bm", label: "Bedriftsmarked" },
              { value: "seeop", label: "SeOpp" },
              { value: "pm", label: "Privatmarked" },
              { value: "ml", label: "Motor" },
            ]}
          />{" "}
        </div>
      </div>
      <div className="tabsTest">
        <Tabs>
          <TabList aria-label="tabs">
            <Attrebut />
            <Tab>Org.nummer</Tab>
            <Tab>Tilbakemelding</Tab>
            <Tab>Score</Tab>
            <Tab>Dato</Tab>
            <Tab>Sted</Tab>
            <Tab>Distributør</Tab>
            <Tab>Tags</Tab>
          </TabList>
          <TabPanel> </TabPanel>
          <TabPanel className="panel">
            <SearchOrgNrHeader filter={filter} />
          </TabPanel>
          <TabPanel className="panel">
            <SearchTekstHeader filter={filter} />
          </TabPanel>
          <TabPanel className="panel">
            <ScoreFilter filter={filter} />
          </TabPanel>
          <TabPanel className="panel">
            <VelgDato filter={filter} />
          </TabPanel>
          <TabPanel className="panel">
            <SearchStedHeader filter={filter} />
          </TabPanel>
          <TabPanel className="panel">
            <VelgDistribtor filter={filter} />
          </TabPanel>
          <TabPanel className="panel">
            <Tagfilter />
          </TabPanel>
        </Tabs>
      </div>
    </header>
  );
};
