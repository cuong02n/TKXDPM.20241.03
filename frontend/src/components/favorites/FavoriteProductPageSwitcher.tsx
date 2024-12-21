import React from "react";
import { ArrowLeft, ArrowRight } from "lucide-react";

interface PageSwitcherProps {
  currentPage: number;
  totalPages: number;
  onNextPage: () => void;
  onPrevPage: () => void;
}

const PageSwitcher: React.FC<PageSwitcherProps> = ({
  currentPage,
  totalPages,
  onNextPage,
  onPrevPage,
}) => {
  return (
    <div className="flex items-center justify-center space-x-2 mt-4 px-4">
      <button
        disabled={currentPage === 1}
        onClick={() => onPrevPage()}
        className="px-3 py-1 bg-blue-500 text-white rounded-md"
      >
        <div className="flex flex-row content-center justify-center">
          <ArrowLeft className="w-fit h-fit" />
          <p>Prev </p>
        </div>
      </button>
      <span>
        {currentPage} / {totalPages}
      </span>
      <button
        disabled={currentPage === totalPages}
        onClick={() => onNextPage()}
        className="px-3 py-1 bg-blue-500 text-white rounded-md"
      >
        <div className="flex flex-row content-center justify-center">
          <ArrowRight className="w-fit h-fit" />
          <p>Prev </p>
        </div>
      </button>
    </div>
  );
};

export default PageSwitcher;
