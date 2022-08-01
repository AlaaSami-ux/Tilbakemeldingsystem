import useSWR from "swr";
import { tbData } from "../components/Body/TbTable";

export const useRequest = () => {
  const fetcher = (url: string) => fetch(url).then((r) => r.json());
  const { data, error } = useSWR<tbData[]>(
    "http://localhost:8080/getAllTilbakemeldinger",
    fetcher,
    {refreshInterval: 100}
  );
  return {
    table: data,
    isLoading: !error && !data,
  };
};
