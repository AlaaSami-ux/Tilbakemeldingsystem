import { useState, useCallback } from "react";

type FetchState = "IDLE" | "PENDING" | "RESOLVED" | "REJECTED";

export const usePost = (url: string, payload: string) => {
    const [state, setState] = useState<FetchState>("IDLE");
    const [data, setData] = useState<any>();
    const [error, setError] = useState<any>();

    const doFetch = useCallback((moreImportantPayload?: string) => {
        setState("PENDING");

        //console.log("fetching", url, moreImportantPayload ?? payload)

        const headers = new Headers();
        headers.append("content-type", "application/json")

        fetch(url, {
            method: "POST",
            body: moreImportantPayload ?? payload,
            headers
        }).then(res => res.json()).then(data => {
            setData(data);
            setState("RESOLVED");
        }).catch(err => {
            setError(err);
            setState("REJECTED")
        })
    }, [url, payload]);

    return {
        state, data, error, fetch: doFetch
    }
}