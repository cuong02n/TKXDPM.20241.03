import React from "react";
import { Image, ArrowLeft, ArrowRight } from "lucide-react";

export const ImageCarousel = ({ urls }: { urls: string[] }) => {
  const [currentImageIndex, setCurrentImageIndex] = React.useState(0);
  const handlePrevious = () => {
    setCurrentImageIndex((prevIndex) =>
      prevIndex === 0 ? urls.length - 1 : prevIndex - 1,
    );
  };

  const handleNext = () => {
    setCurrentImageIndex((prevIndex) =>
      prevIndex === urls.length - 1 ? 0 : prevIndex + 1,
    );
  };
  return (
    <div className="flex w-1/3 h-full relative">
      {urls ? (
        <div className="relative w-full max-h-fit overflow-hidden rounded-md shadow-sm flex items-center justify-center">
          <img
            src={urls[currentImageIndex]}
            alt={`Image ${currentImageIndex + 1}`}
            className="object-contain w-full h-full"
          />
        </div>
      ) : (
        <div className="w-full h-fit rounded-md text-zinc-400 bg-slate-200 flex items-center justify-center">
          <span>No Image Available</span>
        </div>
      )}

      <div className="absolute inset-0 flex justify-between items-center p-1">
        <button
          onClick={handlePrevious}
          className="w-10 h-10 bg-black bg-opacity-50 text-white rounded-full flex justify-center items-center hover:bg-opacity-70 focus:outline-none"
        >
          <ArrowLeft size={20} />
        </button>
        <button
          onClick={handleNext}
          className="w-10 h-10 bg-black bg-opacity-50 text-white rounded-full flex justify-center items-center hover:bg-opacity-70 focus:outline-none"
        >
          <ArrowRight size={20} />
        </button>
      </div>

      <div className="absolute bottom-2 left-1/2 transform -translate-x-1/2 flex gap-2">
        {urls.map((_, index) => (
          <div
            key={index}
            className={`w-2 h-2 rounded-full ${
              index === currentImageIndex ? "bg-blue-500" : "bg-gray-300"
            }`}
          ></div>
        ))}
      </div>
    </div>
  );
};
