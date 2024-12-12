import { Loader2 } from "lucide-react";
export const LoadingPage = () => {
  return (
    <div
      className="flex flex-col w-full h-[360px] items-center justify-center"
      role="status"
    >
      <Loader2 />
      <h1 className="text-lg">Loading...</h1>
    </div>
  );
};
